import java.util.HashMap;
import java.util.Map; 

class HTTPRequest {
    //Required
    private final String url;

    //Optional
    private final String method;
    private final Map<String, String> headers;
    private final Map<String, String> queryParams;
    private final String body;
    private final int timeout;
    private final String[] cacheControl;

    //private constructor
    private HTTPRequest(Builder builder){
        this.url = builder.url;
        this.method = builder.method;
        this.headers = builder.headers;
        this.queryParams = builder.queryParams;
        this.body = builder.body;
        this.timeout = builder.timeout;
        this.cacheControl = builder.cacheControl;
    }

    public static class Builder{
        private final String url;
        private String method = "GET";
        private Map<String, String> headers = new HashMap<>();
        private Map<String,String> queryParams = new HashMap<>();
        private String body;
        private int timeout = 30000;
        private String[] cacheControl = {"public"};

        public Builder(String url){
            this.url = url;
        }

        public Builder method(String method){
            this.method = method;
            return this;
        }

        public Builder body(String body){
            this.body = body;
            return this;
        }

        public Builder addHeader(String Key, String Value){
            this.headers.put(Key, Value);
            return this;
        }

        public Builder addParams(String Key, String Value){
            this.queryParams.put(Key, Value);
            return this;
        }

        public Builder timeout(int timeout){
            this.timeout = timeout;
            return this;
        }

        public Builder cacheControl(String... cacheControl){
            this.cacheControl = cacheControl;
            return this;
        }

        public HTTPRequest build(){
            return new HTTPRequest(this);
        }
    }
}

public class BuilderExample {

    public static void main(String[] args) {
        HTTPRequest request1 = new HTTPRequest.Builder("https://api.example.com/data").build();

        HTTPRequest request2 = new HTTPRequest.Builder("https://api.example.com/Data")
                                .method("POST")
                                .body("Hi")
                                .timeout(15000)
                                .build();

        HTTPRequest request3 = new HTTPRequest.Builder("https://api.example.com/data")
                                .cacheControl("public", "max-age=3600")
                                .build();
    }
}


