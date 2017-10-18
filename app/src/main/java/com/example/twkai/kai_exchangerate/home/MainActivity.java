package com.example.twkai.kai_exchangerate.home;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.example.twkai.kai_exchangerate.Adapter.Country_itemAdapter;
import com.example.twkai.kai_exchangerate.Adapter.Rate_ListAdapter1;
import com.example.twkai.kai_exchangerate.Adapter.Rate_ListAdapter2;
import com.example.twkai.kai_exchangerate.R;
import com.example.twkai.kai_exchangerate.bean.BuySellBean;
import com.example.twkai.kai_exchangerate.databinding.ActivityMainBinding;
import com.example.twkai.kai_exchangerate.fragment.Rate1_Fragment;
import com.example.twkai.kai_exchangerate.fragment.Rate2_Fragment;
import com.example.twkai.kai_exchangerate.utils.BankProvider;
import com.example.twkai.kai_exchangerate.utils.LZString;
import com.example.twkai.kai_exchangerate.utils.Methods;
import com.example.twkai.kai_exchangerate.utils.RecyclerTouchListener;
import com.example.twkai.kai_exchangerate.view.DragLayout;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    public final static String TAG = MainActivity.class.getName();
    private ActivityMainBinding dataBind;
    TabLayout tabLayout;
    private DragLayout dragLayout;
    private Country_itemAdapter country_itemAdapter;
    private FragmentPagerAdapter ratePagerAdapter;
    private  Rate1_Fragment rate1_fragment;
    private  Rate2_Fragment rate2_fragment;

    private Rate_ListAdapter1 rate_listAdapter1;
    private Rate_ListAdapter2 rate_listAdapter2;
    Socket socket;
    boolean isConnected = true;
    boolean isFirst = true;
    public static ArrayList<HashMap<String, Object>> UpdateRateData1 = new ArrayList<HashMap<String, Object>>();
    public static ArrayList<HashMap<String, Object>> UpdateRateData2 = new ArrayList<HashMap<String, Object>>();
    private String Country = "美金", oldCountry = "";
    private int page = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBind = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initDragLayout();
        MFApplication.Dollar =  "";
        startSocket();
        initView();
    }

    private void startSocket(){
        socket = ((MFApplication)getApplication()).getSocket();
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.on(Socket.EVENT_ERROR, onError);
        socket.on("unauthorized", onUnAuthorized);
        socket.on("message", onMessage);
        socket.connect();
    }

    private void initView(){
        // 设置Layout管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataBind.lvCountry.setLayoutManager(layoutManager);//幣值的側邊攔ListView
        country_itemAdapter = new Country_itemAdapter();
        country_itemAdapter.setItems();
        country_itemAdapter.notifyDataSetChanged();
        dataBind.lvCountry.setAdapter(country_itemAdapter);


        dataBind.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dragLayout.open();
            }
        });
        dataBind.lvCountry.addOnItemTouchListener(new RecyclerTouchListener(getBaseContext(), dataBind.lvCountry, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                String country = country_itemAdapter.getItems().get(position).get("Country").toString();
                Toast.makeText(getBaseContext(), position+ " is selected successfully....." + country, Toast.LENGTH_SHORT).show();
                MFApplication.Dollar = country_itemAdapter.getItems().get(position).get("Dollar").toString();
                Country = country_itemAdapter.getItems().get(position).get("Country").toString();

                isFirst = true;
                //handle click event
                dragLayout.close();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        rate1_fragment = new Rate1_Fragment();
        rate2_fragment = new Rate2_Fragment();

        ratePagerAdapter = new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return rate1_fragment;
                    case 1:
                        return rate2_fragment;
                    default:
                        return rate1_fragment;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.Cash);
                    case 1:
                        return getString(R.string.Bk);
                    default:
                        return getString(R.string.Cash);
                }
            }
        };

        dataBind.viewPager.setAdapter(ratePagerAdapter);
        tabLayout = (TabLayout)findViewById(android.R.id.tabs);
        tabLayout.setupWithViewPager(dataBind.viewPager);

        dataBind.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                page = i;
                Log.e("rate1_fragment",String.valueOf(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
        socket.off(Socket.EVENT_CONNECT, onConnect);
        socket.off(Socket.EVENT_DISCONNECT, onDisconnect);
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        socket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        socket.off(Socket.EVENT_ERROR, onError);
        socket.off("unauthorized", onUnAuthorized);

    }

    private void initDragLayout() {
        dragLayout = (DragLayout) findViewById(R.id.dl);
        dragLayout.setDragListener(new DragLayout.DragListener() {
            @Override
            public void onOpen() {
                dataBind.lvCountry.smoothScrollToPosition(new Random().nextInt(7));
            }

            @Override
            public void onClose() {
                shake();
            }

            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(dataBind.btnMenu, 1 - percent);
            }
        });
    }

    private void shake() {
        dataBind.btnMenu.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }

    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            isConnected = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<HashMap<String, Object>> tempRateData = new ArrayList<HashMap<String, Object>>();
                    UpdateRateData1.clear();
                    UpdateRateData2.clear();
                    JSONObject data = (JSONObject) args[0];
                    Iterator<String> iter = data.keys();

//                    iter: {"cathaybk":"ᯡࡊ䆴ɂ̅¬ې⃎ʙ戣ᠠ怽f⹆焠ന搦㠪〦潶㸓橥ဦᠨ㛀ֺ♸ဠ䰡ᴘ捩䩱砇⣹䂠ଠݛ┠愠㤠䵘䑈ᣓ᠀օ拓ᕩųㄓ歄ᴄں㿬嶦揷䟠獗捘䐢婀)T䘾ވᚇ揋喌㐬䜼⒨嵐Ῥ丶搀Ǹ᜙奀䢑㉹澵ࠧࠡР੦┏ฤ嬵漉⓬⥘न綄⤍㑠ౣ岤⧠ၻᗸЖⴆ⅞ᜬ㈥ࠤঠᎭ獂ጅㅟ⻦婂⵱宮⡁൧仁⢨䮇㫝㤰罈癆㶫杒畮䤥憳Ⲧ兙䶠䎖䞠ੋ૬♗䓎࣌垕囊⯄༰%司j呩摠塎เ涰緐珛䔰Åⵣᅂלб᧨擲儌宺⢩䐢䯥栀㲱㛩獼䋞⩅ˤ⢮ⴺ୘剫嚺৽ᩪ\"嚫ᬄ坠䕲䫴矧ठࢂᕉ䳂璤灍⸰䗌ࢫ໵嶻ハ⤢ᆀᥕ⾻类㹟仙䏊Ҋ呉㨬奭䆷⫶潲⹬ᅀ%怤堡㛔ᘳ求䨠ⵑŽ⨕凌ۅ䁌加൫䋉う⠠ ","fubonbk":"ᯡࡊ䆴ɂ̅¬ې⃎ʙ戣ᠠ怽fLX˒焠ന搦㠪〦泶㸐Ӧ㊺‬〨㟀Ɔ䦶чn㺉槹㶉ڪ㖎恸坂唠彺âº㢨⡑㌷杍䀭䡪嚣ୖ䥗敪὘ึᖁ矢樱ᴘ⩂㖗࠭凧戯儠Ũ4朐㩫Ҩ³侏㏣狛啅㨖祿Ҙ䬬儉〒䓵㠡沙㵺H䰠ӎ㓮噪ᑻ⓭徂瑰绹剟ⴰഀ淅Ӹ乹摻嶾澫䙆ዌᎈ2ద㤾㶯䕂毹䚱ඣ氉ㄽ籟榝㷣巎Ẑ牒㻱ဢӠ㎥佔烎㉖ׂᐾහ䔯懆墛∠Õ䃾崅䦁᱂঄劄缢⼨⌑णᠾࠕŉ଍ᇼ漷Ĳ⥨㙖丈䠡戠Ⴊ⟰帉汌乤ᆺ楝栕଄楄庨䂙媐嶴瀩旖ص㍩㊓●∬䎠لᘃ̢Ì棒ⓤ✠⍩撋㴴も⬓⩨␁墆墬➭磏㽇癊ᶃ䳹簅癘❷㕽ⶈ細Êຜ䒞䡂䡫㻶ᾄᵺ偖䀠尠䬠ᛱ䎠ú⥎ňȈ书璠2खⰸ䚊愄㶤\"疔† ","megabk":"ᯡࡊ䆴ɂ̅¬ې⃎ʙ戣ᠠ怽fL峩-҄͘Ր΀歝砤敜唣తᣰƆ䦶лn㸠ภ癒岴ℚ߲ㆀⶼ㹨ᔠठ椸磴⡐孮抵暕㧈皉煖છ礨岟䍍嘂犠廲族珱#䐠䈠՘≙䑧ी·䧙洸เ劜㩦じ⋥ሗ␤㶣⋙ᒠ⭖局Ճࠤ毥䲫∤㘴䪉ᅶ䖵䌋䧷䬤ᵀ⻔愡⋐㧾憱䱋䈡ʀጤ䓻০姡䠻⪨皝⻓燏ᢳ嬿㙔ྻ଱玶⭴⬆緒燧䯥娽奿䞍变ᝄ䔃ᇅ㙠勈簀疚䪡d径ᮆ愰爠⤧בᤌᄾ㪘ტ߭ܲ磬⛂笈吠୵〡ᓺ瑯滣ᓈ䁶kᗢࠠੈ㋓५ᛊڄ䜼ߤ◲⁗彠ƨ䔳⬦◙㧘䏵䜰㕪޵⡀䠲汛⭆ࡺ㤼ဠ嘔⢪妐Ⴤ煒濲櫅ɘ䶧紎঳宦䌎㢇卪嫈<庶䚫䝒㢯砽滀Ⲳ䡇㺿島㤦བವዽ愡Ⱒ暸泄傿䎀氩㮸烴㺊昰㙼㞉ᇙ➋瘑ᬺ碡ن‮厊મ幣䇓㌾夊₿䈠ⳓ≽g⒪➱䨜ゝ敉䨐廀㸰ń偅罾Ԭ祼Ⴁᶏ䇫篋泥穘哢屾筠眐砱㕐䪧ᰠ礷泛ᮒ澣࢑ࣺ➌䔁㐣敠U㢅磿˦ℨ೭ɏ㨶䤅ᯮൺ⦃ಒɈ⒑᱄碊ᒨ䗳ቤ禨㣁泅Ꮲ䣸㙇ᐠ஠ӐÖ㨧䥾ᤠϰ昱ᕀ\"™䦀ᗄ  ","twbk":"ᯡࡊ䆴ɂ̅¬ې⃎ʙ戣ᠠ怽fÌ峩-҄͘Ր΀歝砤敜唣తᣰƆ䦶лn㸠๮岴ℚ߲ㆀⶼⷙᔠठ椸磴⡐孮怮挂㳙咖㔑殒㸙ָ㿩㡥纂䂽峼⡩厱梠Ǥb%᱂ᓤ⎩ဣ㈱䥽㚬笰+·ޣ࣑〮◜Յ着梡㼵ጎીȩ䂭অ兖⏑ો᭣䫡瞙ᰝ湽ㅧ㙌岉⑝毯挀㏔吠愶䝩劍䓛䌨ൂ櫤䮛䳙䟒⒒歖瞹⩏焓户ࢨ⒖礷㛤⊰嵱ͧ䱫箁⤠Ⅱ᪘扢ᯮݴོ䶫稔漭坠ƨቛ㑕䕪幖ၗᖃ䔟Ɋ畱ऐ噠ᅛバ牍ठr墠্䞝傢ඪ䫅⒙ࢵ䄓嘡㼁⚨㴐⍉㢼⋢ٱࠠ婍)⧪▦癆⒑ᶞ㘹ᳱ@H彡噭๞∴ᖙᚊ㌉㠁塣噰夀ᙆ‧ት㺺睶௫窢燽殂應゜ⷸᥚ寘༮㶋ࠠŵ槂஗ᔇ玕睡ใ˥䢀䥐ሞ㊜㮷枨喨ĵ䖔磑䩲枨水丬垥گ䧸䐠䥈ᵼ浣䥢䖅ᅨ巩㦀㬰厨䓶̯ᵼ㢬盛ð砳ᮙᰂ扳䅌劝皾眏乆㽪吡櫸䮌㹹目㔄ш൲▮嫀⾖愛㭬乂剷湚࡜䨤∠୆摷ȉᥲ㋩ᷩ৩〺㴌䷶េ斬ᝌ䢶ᮏѣ২≛㥉ᢶ␵⥿ዪ䦙㊀|E䀥姰憥㉸>䆲磉 न⩫ǔဠ ","chbbk":"ᯡࡊ䆴ɂ̅¬ې⃎ʙ戣ᠠ怽fLř㬂:ࣨڐ઀۩嚛瀩䙥橠ಀࢇ•暳ᘨݲ糅㊘権ಂ曠嬼攠强Ăb*㢨⡑孨΀焏᭩ᦋԂ˙嚛㒠гƯތ⬣䶯䀥へǹ㜔晧ⓐ㱂ἃ†䠣▜☡ঀ䀧᢯ॣ⚩⮇炓␥幥⢹哇ᡣ䷖呙墄൩ဨ຦㿶奁所宲ଌⴲ殜㘰揀౉爎ↆ▩磎ୈ綬ぅ㶎嫱僯ሠ拦弑ม᱐䥴⳹ᑼ旣ᔾM➠ॴ磗格ၴ㛦䥇ဗ浚H啅窹枀䋢ࣸ⎂噏௢ᴠ੠怢㓁□Ǡ塌杏Ҫ愑䢱Ⴢࢊ玾ᜨ吘⪀ᙩᅡ*奇儂索᳇ق睸䂡⨒ శʜ纏䘲੭碠⮍䫫憙⒍⥍塹ⶄ橓઼˪嫋⊺'ᆘᛰ恰䟣ࣖ䋞焃ⶤ玴স䷸徽Ⰸ囤⅏ˊ瘗ૠ俫祫૤ᙁ̜恂᧨ⴞ䞠ƐŌ{̰ᗢँ>䡥戓Რh⁹も݋΁塱⠠ ","esunbk":"ᯡࡊ䆴ɂ̅¬ې⃎ʙ戣ᠠ怽fL峩-҄͘Ր΀歝砤敜唣తᣰƆ䦶лn㺑仙ᑡ稯劂恻ᱹⲠ彊âº㢨⡑㌷怪曩㗺䤲獱ஶY㞄簒ଡ଼羸嗣❀倴㮻瀠沤ℍࠠɠൃާ

                    while (iter.hasNext()) {
                        HashMap<String, Object> oneInfo = new HashMap<String, Object>();
                        String bank = iter.next();
                        int bankID = BankProvider.getBankID(bank);
                        String bankName = BankProvider.getBankName(bank);
                        oneInfo.put("ID", bankID);
                        oneInfo.put("bank", bankName);
                        try {
                            Object value = data.get(bank);
                            String rate_value = LZString.decompressFromUTF16(value.toString());
                            JSONObject rateObj = new JSONObject(rate_value);
                            Iterator iterator = rateObj.keys();
                            while(iterator.hasNext()){
                                String dollar = (String)iterator.next();
                                if(dollar.equals(MFApplication.Dollar)){
                                    oneInfo.put("Dollar", dollar);
                                }
                                try {
                                    JSONObject rate_buysell = rateObj.getJSONObject(dollar);
                                    Gson gson = new Gson();
                                    BuySellBean buySellBean = gson.fromJson(rate_buysell.toString(), BuySellBean.class);

                                    if(dollar.equals(MFApplication.Dollar)){

                                        oneInfo.put("bkbuy", bkbuy);
                                        oneInfo.put("bksell", bksell);
                                        oneInfo.put("cashbuy", cashbuy);
                                        oneInfo.put("cashsell", cashsell);
                                        tempRateData.add(oneInfo);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }
                    UpdateRateData1 = tempRateData;
                    UpdateRateData2 = tempRateData;
                    if(!oldCountry.equals(Country)){
                        oldCountry = Country;
                        rate1_fragment.showDollar(Country);
                        rate2_fragment.showDollar(Country);
                    }
                    if(isFirst == true){
                        isFirst = false;
                        Methods.Ascending(UpdateRateData1, MFApplication.CashSortKey);
                        rate_listAdapter1 = new Rate_ListAdapter1(UpdateRateData1);
                        Methods.Ascending(UpdateRateData2, MFApplication.BkSortKey);
                        rate_listAdapter2 = new Rate_ListAdapter2(UpdateRateData2);
                        rate1_fragment.showView(rate_listAdapter1);
                        rate2_fragment.showView(rate_listAdapter2);
                        rate1_fragment.showDollar(Country);
                        rate2_fragment.showDollar(Country);
                    }
                    if(page == 0){
                        sortRate(MFApplication.CashSortKey);
                        rate_listAdapter1.swapItems( UpdateRateData1);
                    }else{
                        sortRate(MFApplication.BkSortKey);
                        rate_listAdapter2.swapItems( UpdateRateData2);
                    }

                }
            });
        }
    };

    public void sortRate(String key){
        switch (key) {
            case "cashbuy":
                Methods.Descending(UpdateRateData1, "cashbuy");
                break;
            case "bkbuy":
                Methods.Descending(UpdateRateData2, "bkbuy");
                break;
            case "cashsell":
                Methods.Ascending(UpdateRateData1, "cashsell");
                break;
            case "bksell":
                Methods.Ascending(UpdateRateData2, "bksell");
                break;

            default:
                Methods.Descending(UpdateRateData1, "cashbuy");
                break;
        }
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = true;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Connected...", Toast.LENGTH_LONG)
                            .show();
                    isConnected = true;

                    // socket.emit("Welcome", argPhone);
                }
            });
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = false;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Disconnected....", Toast.LENGTH_LONG)
                            .show();
                    isConnected = false;
                }
            });
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            isConnected = false;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getBaseContext(), "Failed to connect...", Toast.LENGTH_LONG)
                            .show();
                    isConnected = false;
                }
            });
        }
    };
    private Emitter.Listener onError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };
    private Emitter.Listener onUnAuthorized = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];

                    Toast.makeText(getBaseContext(), data, Toast.LENGTH_LONG)
                            .show();
                }
            });
        }
    };

}
