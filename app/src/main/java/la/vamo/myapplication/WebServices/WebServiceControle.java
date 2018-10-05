package la.vamo.myapplication.WebServices;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import la.vamo.myapplication.WebServices.content.Data;
import la.vamo.myapplication.WebServices.content.TesteSquidexInfo;
import la.vamo.myapplication.WebServices.content.Token;

public class WebServiceControle {
    /**
     * Responsável por gerenciar as threads que realizam as chamadas web
     */
    private static RequestQueue requestQueue;
    private static Token token;

    public RequestQueue getRequestQueueInstance(Context context)
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context);
        return requestQueue;
    }

    public void carregaListaTeste(final Context context
            , final CarregaListaTesteListener carregaListaTesteListener)
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {
                    carregaListaTeste(context, carregaListaTesteListener);
                }

                @Override
                public void onErro()
                {
                    if (carregaListaTesteListener != null)
                        carregaListaTesteListener.onErro();
                }
            });
        }
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://cloud.squidex.io/api/content/consumo/teste",
                    null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            if (carregaListaTesteListener != null)
                                carregaListaTesteListener.onResultOk(new Gson().fromJson(response.toString(), TesteSquidexInfo.class));
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (carregaListaTesteListener != null)
                                carregaListaTesteListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(jsonObjectRequest);
        }
    }

    public void criaTeste(final Context context, final Data data, final UpdateTesteListener criaTesteListener) throws JSONException
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {
                    try
                    {
                        criaTeste(context, data, criaTesteListener);
                    }
                    catch (JSONException e)
                    {
                        if (criaTesteListener != null)
                            criaTesteListener.onErro();
                    }
                }

                @Override
                public void onErro()
                {
                    if (criaTesteListener != null)
                        criaTesteListener.onErro();
                }
            });
        }
        else
        {
            JsonObjectRequest jsonObjectRequest
                    = new JsonObjectRequest(Request.Method.POST,
                    "https://cloud.squidex.io/api/content/consumo/teste?publish=true",
                    new JSONObject(new Gson().toJson(data)),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            if (criaTesteListener != null)
                                criaTesteListener.onResultOk();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (criaTesteListener != null)
                                criaTesteListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(jsonObjectRequest);
        }
    }

    public void atualizaTeste(final Context context, final Data data, final String id, final UpdateTesteListener atualizaTesteListener) throws JSONException
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {
                    try
                    {
                        atualizaTeste(context, data, id, atualizaTesteListener);
                    }
                    catch (JSONException e)
                    {
                        if (atualizaTesteListener != null)
                            atualizaTesteListener.onErro();
                    }
                }

                @Override
                public void onErro()
                {
                    if (atualizaTesteListener != null)
                        atualizaTesteListener.onErro();
                }
            });
        }
        else
        {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                    "https://cloud.squidex.io/api/content/consumo/teste/" + id,
                    new JSONObject(new Gson().toJson(data)),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            if (atualizaTesteListener != null)
                                atualizaTesteListener.onResultOk();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (atualizaTesteListener != null)
                                atualizaTesteListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(jsonObjectRequest);
        }
    }

    public void deletaTeste(final Context context, final String id, final UpdateTesteListener deleteTesteListener)
    {
        if (token == null)
        {
            geraToken(context, new GeraTokenListener()
            {
                @Override
                public void onTokenOk()
                {

                    deletaTeste(context, id, deleteTesteListener);

                }

                @Override
                public void onErro()
                {
                    if (deleteTesteListener != null)
                        deleteTesteListener.onErro();
                }
            });
        }
        else
        {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE,
                    "https://cloud.squidex.io/api/content/consumo/teste/" + id,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            if (deleteTesteListener != null)
                                deleteTesteListener.onResultOk();
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            if (deleteTesteListener != null)
                                deleteTesteListener.onErro();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError
                {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", token.getToken_type() + " " + token.getAccess_token());
                    return headers;
                }
            };
            getRequestQueueInstance(context).add(stringRequest);
        }
    }

    private void geraToken(Context context, final GeraTokenListener geraTokenListener)
    {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                "https://cloud.squidex.io/identity-server/connect/token",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        token = new Gson().fromJson(response, Token.class);
                        if (geraTokenListener != null)
                            geraTokenListener.onTokenOk();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        if (geraTokenListener != null)
                            geraTokenListener.onErro();
                    }
                }
        )
        {
            @Override
            public String getBodyContentType()
            {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("grant_type", "client_credentials");
                params.put("client_id", "consumo:teste");
                params.put("client_secret", "WkE/XbnYEqH2EUm//t58esEsh2MlWKcrAxUNCCnRAUU=");
                params.put("scope", "squidex-api");
                return params;
            }
        };
        //
        getRequestQueueInstance(context).add(stringRequest);
    }


    public interface GeraTokenListener
    {
        public abstract void onTokenOk();

        public abstract void onErro();
    }

    public interface CarregaListaTesteListener
    {
        public abstract void onResultOk(TesteSquidexInfo teste);

        public abstract void onErro();
    }

    public interface UpdateTesteListener
    {
        public abstract void onResultOk();

        public abstract void onErro();
    }

}
