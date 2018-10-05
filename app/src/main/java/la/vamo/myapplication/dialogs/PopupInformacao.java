package la.vamo.myapplication.dialogs;

import android.content.Context;
import android.widget.Toast;

public class PopupInformacao {
    public static void mostraMensagem(Context context, String mensagem)
    {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }
}
