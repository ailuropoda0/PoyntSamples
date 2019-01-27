package co.poynt.samples.codesamples;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import co.poynt.api.model.Card;
import co.poynt.api.model.CardType;
import co.poynt.api.model.FundingSourceAccountType;
import co.poynt.api.model.FundingSourceType;
import co.poynt.api.model.Order;
import co.poynt.api.model.Transaction;
import co.poynt.api.model.TransactionAction;
import co.poynt.os.contentproviders.orders.transactionreferences.TransactionreferencesColumns;
import co.poynt.os.model.Intents;
import co.poynt.os.model.Payment;
import co.poynt.os.model.PaymentStatus;
import co.poynt.os.model.PoyntError;
import co.poynt.os.services.v1.IPoyntOrderService;
import co.poynt.os.services.v1.IPoyntOrderServiceListener;
import co.poynt.os.services.v1.IPoyntTransactionService;
import co.poynt.os.services.v1.IPoyntTransactionServiceListener;
import co.poynt.samples.codesamples.utils.Util;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.HeaderProperty;

import co.poynt.samples.hapiApi.api.ProfileApi;
import co.poynt.samples.hapiApi.Configuration;
import co.poynt.samples.hapiApi.model.Profile;
import co.poynt.samples.hapiApi.auth.ApiKeyAuth;
import co.poynt.samples.hapiApi.ApiClient;
import co.poynt.samples.hapiApi.ApiException;

public class PaymentActivity extends Activity {

    // request code for payment service activity
    private static final int COLLECT_PAYMENT_REQUEST = 13132;
    private static final int ZERO_DOLLAR_AUTH_REQUEST = 13133;
    private static final String TAG = PaymentActivity.class.getSimpleName();


    private static final String SOAP_ACTION = "https://ich95-oho5-prod-env1-osb.hospitality.oracleindustry.com/OPERAOSB/OC_CRM/OperaCRMServices/ProfileService";
    private static final String METHOD_NAME = "fetchProfiles";
    private static final String WSDL_TARGET_NAMESPACE = "https://ich95-oho5-prod-env1-osb.hospitality.oracleindustry.com/OPERAOSB/OC_CRM/OperaCRMServices/";
    private static final String SOAP_ADDRESS = "https://ich95-oho5-prod-env1-osb.hospitality.oracleindustry.com/OPERAOSB/OC_CRM/OperaCRMServices/ProfileService?WSDL";


    private IPoyntTransactionService mTransactionService;
    private IPoyntOrderService mOrderService;


    private Spinner temperatureSpinner;
    private Spinner lightSpinner;

    Button chargeBtn;
    TextView orderSavedStatus;

    private Gson gson;

    String firstName;
    String lastReferenceId;


    /*
     * Class for interacting with the OrderService
     */
    private ServiceConnection mOrderServiceConnection = new ServiceConnection() {
        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "PoyntOrderService is now connected");
            // this gets an instance of the IRemoteInterface, which we can use to call on the service
            mOrderService = IPoyntOrderService.Stub.asInterface(service);
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "PoyntOrderService has unexpectedly disconnected");
            mOrderService = null;
        }
    };
    private IPoyntOrderServiceListener saveOrderCallback = new IPoyntOrderServiceListener.Stub() {
        public void orderResponse(Order order, String s, PoyntError poyntError) throws RemoteException {
            if (order == null) {
                Log.d("orderListener", "poyntError: " + (poyntError == null ? "" : poyntError.toString()));
            }else{
                Log.d(TAG, "orderResponse: " + order);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderSavedStatus.setText("ORDER SAVED");
                    }
                });
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* payment page */
        launchPoyntPayment(10l, null);

        setContentView(R.layout.activity_payment);


        //var view = LayoutInflater.Inflate(Resource.Layout.spinnerItem, null, false);

        temperatureSpinner = (Spinner) findViewById(R.id.temperatureSpinner);
        ArrayAdapter<CharSequence> temperatureAdapter = ArrayAdapter.createFromResource(this,
                R.array.temperature_array, android.R.layout.simple_spinner_item);
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(temperatureAdapter);

        lightSpinner = (Spinner) findViewById(R.id.lightSpinner);
        ArrayAdapter<CharSequence> lightAdapter = ArrayAdapter.createFromResource(this,
                R.array.light_array, android.R.layout.simple_spinner_item);
        lightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lightSpinner.setAdapter(lightAdapter);



        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        gson = new GsonBuilder().setPrettyPrinting().create();
        gson = new Gson();
        chargeBtn = (Button) findViewById(R.id.chargeBtn);
        chargeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //hapi();

                Intent intent = new Intent(PaymentActivity.this, CompleteActivity.class);

                String temperature = temperatureSpinner.getSelectedItem().toString();
                intent.putExtra("ROOM_TEMPERATURE", temperature);
                String light = lightSpinner.getSelectedItem().toString();
                intent.putExtra("ROOM_LIGHT", light);
                intent.putExtra("FIRST_NAME", firstName);
                startActivity(intent);
            }
        });

        chargeBtn.setEnabled(true);
/*

        launchRegisterBtn = (Button) findViewById(R.id.launchRegisterBtn);
        // Only works if Poynt Register does not have an active order in progress
        launchRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Product product = Util.createProduct();
                Intent intent = new Intent();
                intent.setAction(Intents.ACTION_ADD_PRODUCT_TO_CART);
                intent.putExtra(Intents.INTENT_EXTRA_PRODUCT, product);
                intent.putExtra(Intents.INTENT_EXTRA_QUANTITY, 2.0f);
                startActivity(intent);
            }
        });
*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindServices();
    }

    private void bindServices() {
        bindService(Intents.getComponentIntent(Intents.COMPONENT_POYNT_ORDER_SERVICE),
                mOrderServiceConnection, Context.BIND_AUTO_CREATE);
        bindService(Intents.getComponentIntent(Intents.COMPONENT_POYNT_TRANSACTION_SERVICE),
                mTransactionServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindServices();
    }

    private void unbindServices() {
        unbindService(mOrderServiceConnection);
        unbindService(mTransactionServiceConnection);
    }

    private class SaveOrderTask extends AsyncTask<Order, Void, Void> {
        protected Void doInBackground(Order... params) {
            Order order = params[0];
            String requestId = UUID.randomUUID().toString();
            if (mOrderService != null) {
                try {
                    mOrderService.createOrder(order, requestId, saveOrderCallback);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private IPoyntTransactionServiceListener mTransactionServiceListener = new IPoyntTransactionServiceListener.Stub() {
        public void onResponse(Transaction _transaction, String s, PoyntError poyntError) throws RemoteException {
            Gson gson = new Gson();
            Type transactionType = new TypeToken<Transaction>() {
            }.getType();
            String transactionJson = gson.toJson(_transaction, transactionType);
            Log.d(TAG, "onResponse: " + transactionJson);
            Log.d(TAG, "onResponse: " + _transaction);

        }

        //@Override
        public void onLaunchActivity(Intent intent, String s) throws RemoteException {
            //do nothing
        }

        public void onLoginRequired() throws RemoteException {
            Log.d(TAG, "onLoginRequired called");
        }

    };

    public void getTransaction(String txnId) {
        try {

            mTransactionService.getTransaction(txnId, UUID.randomUUID().toString(),
                    mTransactionServiceListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private ServiceConnection mTransactionServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mTransactionService = IPoyntTransactionService.Stub.asInterface(iBinder);


            try {
                mTransactionService.getTransaction("fcf98959-c188-42d1-b085-786d21e552ac", UUID.randomUUID().toString(), mTransactionServiceListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            mTransactionService = null;
        }
    };

    private void launchPoyntPayment(long amount, Order order) {
        String currencyCode = NumberFormat.getCurrencyInstance().getCurrency().getCurrencyCode();

        Payment payment = new Payment();
        lastReferenceId = UUID.randomUUID().toString();
        payment.setReferenceId(lastReferenceId);

        payment.setCurrency(currencyCode);
        // enable multi-tender in payment options
        payment.setMultiTender(true);

        if (order != null) {
            payment.setOrder(order);
            payment.setOrderId(order.getId().toString());

            // tip can be preset
            //payment.setTipAmount(500l);
            payment.setAmount(order.getAmounts().getNetTotal());
        } else {
            // some random amount
            payment.setAmount(amount);

            // here's how tip can be disabled for tip enabled merchants
            // payment.setDisableTip(true);
            payment.setDisableTip(true);
        }

        //payment.setSkipSignatureScreen(true);
        //payment.setSkipReceiptScreen(true);
        //payment.setSkipPaymentConfirmationScreen(true);

        payment.setCallerPackageName("co.poynt.sample");
        Map<String, String> processorOptions = new HashMap<>();
        processorOptions.put("installments", "2");
        processorOptions.put("type", "emi");
        processorOptions.put("originalAmount", "2400");
        payment.setProcessorOptions(processorOptions);

        // start Payment activity for result
        try {
            Intent collectPaymentIntent = new Intent(Intents.ACTION_COLLECT_PAYMENT);
            collectPaymentIntent.putExtra(Intents.INTENT_EXTRAS_PAYMENT, payment);
            startActivityForResult(collectPaymentIntent, COLLECT_PAYMENT_REQUEST);
        } catch (ActivityNotFoundException ex) {
            Log.e(TAG, "Poynt Payment Activity not found - did you install PoyntServices?", ex);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // payment finished
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "Received onActivityResult (" + requestCode + ")");
        // Check which request we're responding to
        if (requestCode == COLLECT_PAYMENT_REQUEST) {
            logData("Received onActivityResult from Payment Action");
            // Make sure the request was successful
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    Payment payment = data.getParcelableExtra(Intents.INTENT_EXTRAS_PAYMENT);

                    if (payment != null) {
                        //save order
                        if (payment.getOrder() != null) {
                            new SaveOrderTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, payment.getOrder());
                        }

//                      Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Gson gson = new Gson();
//                        Type paymentType = new TypeToken<Payment>() {
//                        }.getType();
//                        Log.d(TAG, gson.toJson(payment, paymentType));
                        Log.d(TAG, "onActivityResult: " + payment.getTransactions().get(0));
                        for (Transaction t : payment.getTransactions()) {
                            Type txnType = new TypeToken<Transaction>() {
                            }.getType();
                            Log.d(TAG, "onActivityResult: transaction: " + gson.toJson(t, txnType));

                            getTransaction(t.getId().toString());
                            //Log.d(TAG, "Card token: " + t.getProcessorResponse().getCardToken());
                            FundingSourceAccountType fsAccountType = t.getFundingSource().getAccountType();
                            if (t.getFundingSource().getCard() != null) {
                                Card c = t.getFundingSource().getCard();
                                String numberMasked = c.getNumberMasked();
                                String approvalCode = t.getApprovalCode();
                                CardType cardType = c.getType();
                                switch (cardType) {
                                    case AMERICAN_EXPRESS:
                                        // amex
                                        break;
                                    case VISA:
                                        // visa
                                        break;
                                    case MASTERCARD:
                                        // MC
                                        break;
                                    case DISCOVER:
                                        // discover
                                        break;
                                }
                            }

                        }

                        Log.d(TAG, "Received onPaymentAction from PaymentFragment w/ Status("
                                + payment.getStatus() + ")");
                        if (payment.getStatus().equals(PaymentStatus.COMPLETED)
                                || payment.getStatus().equals(PaymentStatus.AUTHORIZED)) {

                            //  payment is successful

                            logData("Payment Authorized");
                            List<Transaction> transactions = payment.getTransactions();
                            for(Transaction transaction: transactions){
                                if(transaction.getFundingSource().getType() == FundingSourceType.CREDIT_DEBIT) {
                                    Card card = transaction.getFundingSource().getCard();
                                    logData(card.toString());
                                    logData(card.getCardHolderFullName());
                                    firstName = card.getCardHolderFirstName();
                                    TextView tv0 = (TextView)findViewById(R.id.nameTextView);
                                    tv0.setText(firstName);
                                    logData(Long.toString(transaction.getCustomerUserId()));
                                }
                            }
                            Toast.makeText(getApplicationContext(), "Payment is successful", Toast.LENGTH_LONG).show();

                        } else if (payment.getStatus().equals(PaymentStatus.CANCELED)) {
                            logData("Payment Canceled");
                        } else if (payment.getStatus().equals(PaymentStatus.FAILED)) {
                            logData("Payment Failed");
                        } else if (payment.getStatus().equals(PaymentStatus.REFUNDED)) {
                            logData("Payment Refunded");
                        } else if (payment.getStatus().equals(PaymentStatus.VOIDED)) {
                            logData("Payment Voided");
                        } else {
                            logData("Payment Completed");
                        }
                    } else {
                        // This should not happen, but in case it does, handle it using Content Provider
                        getTransactionFromContentProvider();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                logData("Payment Canceled");
            }
        } else if (requestCode == ZERO_DOLLAR_AUTH_REQUEST) {
            Log.d(TAG, "onActivityResult: $0 auth request");
            if (resultCode == Activity.RESULT_OK) {
                Payment payment = data.getParcelableExtra(Intents.INTENT_EXTRAS_PAYMENT);
                Gson gson = new Gson();
                Type paymentType = new TypeToken<Payment>() {
                }.getType();
                Log.d(TAG, gson.toJson(payment, paymentType));
            }
        }
    }

    private void hapi() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: Bearer
        ApiKeyAuth Bearer = (ApiKeyAuth) defaultClient.getAuthentication("Bearer");
        Bearer.setApiKey("4a8ed04a-4f66-8d77-1832-40442363d026");
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//Bearer.setApiKeyPrefix("Token");

        ProfileApi apiInstance = new ProfileApi();
        String propertyCode = "FRESNO"; // String | Property code as designated in the PMS
        String id = "19369"; // String | PMS profile id
        try {
            Profile result = apiInstance.getProfile(propertyCode, id);
            System.out.println(result.toString());
            logData(result.toString());
        } catch (ApiException e) {
            System.err.println("Exception when calling ProfileApi#getProfile");
            e.printStackTrace();
        }
    }
/*
    private void checkInPage() {
        setContentView(R.layout.activity_payment);

        //var view = LayoutInflater.Inflate(Resource.Layout.spinnerItem, null, false);

        temperatureSpinner = (Spinner) findViewById(R.id.temperatureSpinner);
        ArrayAdapter<CharSequence> temperatureAdapter = ArrayAdapter.createFromResource(this,
                R.array.temperature_array, android.R.layout.simple_spinner_item);
        temperatureAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperatureSpinner.setAdapter(temperatureAdapter);

        lightSpinner = (Spinner) findViewById(R.id.lightSpinner);
        ArrayAdapter<CharSequence> lightAdapter = ArrayAdapter.createFromResource(this,
                R.array.light_array, android.R.layout.simple_spinner_item);
        lightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lightSpinner.setAdapter(lightAdapter);



        android.app.ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

//        gson = new GsonBuilder().setPrettyPrinting().create();
        gson = new Gson();
        chargeBtn = (Button) findViewById(R.id.chargeBtn);
        chargeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                afterPayment();
            }
        });

        chargeBtn.setEnabled(true);
    }

    private void afterPayment(Payment payment) {
        List<Transaction> transactions = payment.getTransactions();
        String temperature = temperatureSpinner.getSelectedItem().toString();
        String light = lightSpinner.getSelectedItem().toString();
        String name;

        for(Transaction transaction: transactions){
            if(transaction.getFundingSource().getType() == FundingSourceType.CREDIT_DEBIT) {
                Card card = transaction.getFundingSource().getCard();
                name = card.getCardHolderFullName();
                logData(card.toString());
                logData(card.getCardHolderFullName());
                logData(Long.toString(transaction.getCustomerUserId()));
            }
        }
    }
*/

    // SOAP
    private void afterPayment() {
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

        PropertyInfo info = new PropertyInfo();


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(SOAP_ADDRESS);

        logData(request.toString());
/*
        try
        {
            List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
            headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode("username:password".getBytes())));

            httpTransport.call(SOAP_ACTION, envelope, headerList);
            SoapObject response = (SoapObject)envelope.getResponse();
            String res = response.toString();
            //response.getProperty(0).toString();
            logData(res);
        }
        catch(Exception e) {
            String ex = e.toString();
        }*/
    }



    /**
     * pulls transaction Ids by referenceId from the content provider
     */
    private void getTransactionFromContentProvider() {
        ContentResolver resolver = getContentResolver();
        String[] projection = new String[]{TransactionreferencesColumns.TRANSACTIONID};
        Cursor cursor = resolver.query(TransactionreferencesColumns.CONTENT_URI,
                projection,
                TransactionreferencesColumns.REFERENCEID + " = ?",
                new String[]{lastReferenceId},
                null);
        List<String> transactions = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                transactions.add(cursor.getString(0));
            }
        }

        cursor.close();

        // handle transactions
        // full transaction can get retrieved using IPoyntTransactionService.getTransaction
        if (!transactions.isEmpty()) {
            logData("Found the following transactions for referenceId " + lastReferenceId + ": ");
            for (String txnId : transactions) {
                logData(txnId);
            }
        } else {
            logData("No Transactions found");
        }
    }

    public void logData(final String data) {
        Log.d(TAG, data);
    }

}
