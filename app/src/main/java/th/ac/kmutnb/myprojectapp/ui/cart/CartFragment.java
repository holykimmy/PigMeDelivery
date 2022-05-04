package th.ac.kmutnb.myprojectapp.ui.cart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import th.ac.kmutnb.myprojectapp.CartActivity;
import th.ac.kmutnb.myprojectapp.MainActivity;
import th.ac.kmutnb.myprojectapp.R;
import th.ac.kmutnb.myprojectapp.databinding.FragmentCartBinding;
import th.ac.kmutnb.myprojectapp.ui.categoryfood.ChickenFragment;
import th.ac.kmutnb.myprojectapp.ui.home.ListAllfood;

public class CartFragment extends Fragment {

    private FragmentCartBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CartViewModel cartViewModel =
                new ViewModelProvider(this).get(CartViewModel.class);

        binding = FragmentCartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        WebView wv = (WebView)root.findViewById(R.id.cart_webview);
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String myemail = preferences2.getString("EMAIL", null);

        String urlStr = "http://www.mywebapp.lnw.mn/cart.php?email=" + myemail;
        wv.setWebViewClient(new CallBack());
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setDefaultFontSize(14);
        wv.getSettings().setBuiltInZoomControls(false);
        wv.getSettings().setSupportZoom(false);
        wv.loadUrl(urlStr);

        return root;
    }

    public class CallBack extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("http://exitcart")) {
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            } else {
                view.loadUrl(url);
            }
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}