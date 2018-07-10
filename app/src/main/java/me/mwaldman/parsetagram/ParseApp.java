package me.mwaldman.parsetagram;

import android.app.Application;

import com.parse.Parse;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("mw130")
                .clientKey("michael")
                .server("http://fbuparsetagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
