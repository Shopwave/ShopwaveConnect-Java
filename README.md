ShopwaveConnect-Java
====================

<p>A Java library for ShopwaveConnect with an accompanying GAE Servlet example project.</p>

<h2>Required Class Libraries</h2>

<p>Each of the following libraries must be included in your JAVA implementation file. An example of this can be found in <strong>Examples/ServletWebApplication/src/com/getshopwave/example/servletwebapplication/WebApplicationServlet.java</strong>.</p>

```java
import com.getshopwave.shopwaveconnect.ShopwaveConnectManager;
import com.getshopwave.shopwaveconnect.httprequest.HttpRequestDelegate;
import com.getshopwave.shopwaveconnect.model.Token;
```

<h2>Required Parameters</h2>

<p>Each of the following parameters will have to be supplied in the code in order to communicate with the ShopwaveConnect API. An example of this can be found in <strong>Examples/ServletWebApplication/src/com/getshopwave/example/servletwebapplication/Config.java</strong>.</p>

```java
/* Your Shopwave ClientIdentifier (e.g. js7woa9ro028djsnakf778sn3wiam3ond274knao) */
public final static String redirectUri = "SHOPWAVE_REDIRECT_URI";
	
/* Your Shopwave ClientSecret (e.g. 76h4389732ru2039r20ruju023r9u2309jk8sna0) */
public final static String clientIdentifier = "SHOPWAVE_CLIENT_IDENTIFIER";
	
/* Your Shopwave RedirectUri (e.g. http://my.app) */
public final static String clientSecret = "SHOPWAVE_CLIENT_SECRET";
    
/* Your Shopwave Scope */
public final static String scope = "user,application,merchant,store,product,category,basket,promotion"; 
```
<h2>Using the Library</h2>

<p>Each of the following code snipets can be found in <strong>Examples/ServletWebApplication/src/com/getshopwave/example/servletwebapplication</strong>.

<h3>Initialisation</h3>

```java
ShopwaveConnectManager connector = new ShopwaveConnectManager();
```

<h3>Authorise</h3>

<h4>ServletWebApplication</h4>

```java
response.sendRedirect(connector.getAuthoriseApplicationUri(Config.redirectUri, Config.clientIdentifier, Config.scope));
```

<h3>Fetch Token</h3>

```java
Token token = connector.makeTokenCall(Config.redirectUri, Config.clientIdentifier, Config.clientSecret, Config.scope, authCode, null);
```

<h3>Make API Call</h3>

```java

connector.makeShopwaveApiCall("API_ENDPOINT", "OAUTH2_TOKEN", "METHOD", "PARAMS_HASHMAP", "HEADERS_HASHMAP", "HTTP_CALLBACK_DELEGATE")
connector.makeShopwaveApiCall("/user", token, "GET", null, headers, null);
```
