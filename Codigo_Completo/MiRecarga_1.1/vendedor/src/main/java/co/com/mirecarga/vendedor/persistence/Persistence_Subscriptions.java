package co.com.mirecarga.vendedor.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import javax.inject.Singleton;

@Singleton
public class Persistence_Subscriptions extends Persistence {

    private final String TAG = Persistence_Subscriptions.class.getSimpleName();

    /**
     * Table column for ID
     **/
    private final String SUBSCRIPTIONS_COLUMN_ID = "_id";
    /**
     * Table column for client handle
     **/
    private final String SUBSCRIPTIONS_COLUMN_CLIENT_HANDLE = "clientHandle";
    /**
     * Table column for host
     **/
    private final String SUBSCRIPTIONS_COLUMN_HOST = "host";
    /**
     * Table column for subscription topic
     **/
    private final String SUBSCRIPTIONS_COLUMN_TOPIC = "topic";
    /**
     * Table column for the subscription qos
     **/
    private final String SUBSCRIPTIONS_COLUMN_QOS = "qos";
    /**
     * Table column for the subscription enable notification setting
     **/
    private final String SUBSCRIPTIONS_COLUMN_NOTIFY = "notify";

    public String getSUBSCRIPTIONS_COLUMN_ID() {
        return SUBSCRIPTIONS_COLUMN_ID;
    }

    public String getSubscriptionsColumnClientHandle() {
        return SUBSCRIPTIONS_COLUMN_CLIENT_HANDLE;
    }

    public String getSubscriptionsColumnHost() {
        return SUBSCRIPTIONS_COLUMN_HOST;
    }

    public String getSubscriptionsColumnTopic() {
        return SUBSCRIPTIONS_COLUMN_TOPIC;
    }

    public String getSubscriptionsColumnNotify() {
        return SUBSCRIPTIONS_COLUMN_NOTIFY;
    }

    public String getSubscriptionsColumnQos() {
        return SUBSCRIPTIONS_COLUMN_QOS;
    }

    public String[] getSubscriptionColumns() {
        return subscriptionColumns;
    }

    public String getSubscriptionWhereQuery() {
        return subscriptionWhereQuery;
    }

    public String getSQL_CREATE_SUBSCRIPTION_ENTRIES() {
        return SQL_CREATE_SUBSCRIPTION_ENTRIES;
    }

    // Columns to return for subscription
    String[] subscriptionColumns = {
            SUBSCRIPTIONS_COLUMN_CLIENT_HANDLE,
            SUBSCRIPTIONS_COLUMN_TOPIC,
            SUBSCRIPTIONS_COLUMN_NOTIFY,
            SUBSCRIPTIONS_COLUMN_QOS,
            SUBSCRIPTIONS_COLUMN_ID
    };

    String subscriptionWhereQuery = SUBSCRIPTIONS_COLUMN_CLIENT_HANDLE + "=?";

    /**
     * Creates the persistence object passing it a context
     *
     * @param context Context that the application is running in
     */
    public Persistence_Subscriptions(Context context) {
        super(context);
    }

    /**
     * Table suscriptions
     **/
    final String SQL_CREATE_SUBSCRIPTION_ENTRIES =
            CREATE_TABLE + TABLE_SUBSCRIPTIONS + PARENTHESIS_INI +
                    SUBSCRIPTIONS_COLUMN_ID + INT_TYPE + CONSTRAINT_PK + COMMA_SEP +
                    SUBSCRIPTIONS_COLUMN_CLIENT_HANDLE + TEXT_TYPE + COMMA_SEP +
                    SUBSCRIPTIONS_COLUMN_HOST + TEXT_TYPE + COMMA_SEP +
                    SUBSCRIPTIONS_COLUMN_TOPIC + TEXT_TYPE + COMMA_SEP +
                    SUBSCRIPTIONS_COLUMN_NOTIFY + INT_TYPE + COMMA_SEP +
                    SUBSCRIPTIONS_COLUMN_QOS + INT_TYPE +
                    PARENTHESIS_FSC;

    /**
     * Persist a Subscription to the database
     *
     * @param subscription the subscription to persist
     * @throws PersistenceException If storing the data fails
     */
    public long persistSubscription(Subscription subscription) throws PersistenceException {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUBSCRIPTIONS_COLUMN_CLIENT_HANDLE, subscription.getClientHandle());
        values.put(SUBSCRIPTIONS_COLUMN_TOPIC, subscription.getTopic());
        values.put(SUBSCRIPTIONS_COLUMN_NOTIFY, subscription.isEnableNotifications() ? 1 : 0); //convert boolean to int and then put in values
        values.put(SUBSCRIPTIONS_COLUMN_QOS, subscription.getQos());

        long newRowId = db.insert(TABLE_SUBSCRIPTIONS, null, values);
        db.close();
        if (newRowId == -1) {
            throw new PersistenceException("Failed to persist subscription: " + subscription.toString());
        } else {
            subscription.setPersistenceId(newRowId);
            return newRowId;
        }
    }

    /**
     * Deletes a subscription from the database
     *
     * @param subscription The subscription to delete from the database
     */
    public void deleteSubscription(Subscription subscription) {
        Log.d(TAG, "Deleting Subscription: " + subscription.toString());
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_SUBSCRIPTIONS, SUBSCRIPTIONS_COLUMN_ID + "=?", new String[]{String.valueOf(subscription.getPersistenceId())});
        db.close();
        //don't care if it failed, means it's not in the db therefore no need to delete
    }
}
