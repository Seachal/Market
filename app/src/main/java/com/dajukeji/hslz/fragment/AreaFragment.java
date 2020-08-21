package com.dajukeji.hslz.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.area.CityInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地区Fragment
 */
public class AreaFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String ARG_PARAM1 = "parentCode";
    @BindView(R.id.refresh_list_view)
    ListView mRefreshListView;
    @BindView(R.id.loadingBar)
    ProgressBar mLoadingBar;

    private String mParam1;

    private OnFragmentInteractionListener mListener;

    private AreaAdapter adapter;
    private List<CityInfo> cityInfos;
    public AreaFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AreaFragment.
     */
    public static AreaFragment newInstance(List<CityInfo> cityInfos) {
        AreaFragment fragment = new AreaFragment();
        Bundle args = new Bundle();
        fragment.cityInfos =cityInfos;
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_area, container, false);
        ButterKnife.bind(this, view);
        mRefreshListView.setOnItemClickListener(this);

//        FormEncodingBuilder builder = new FormEncodingBuilder();
//        builder.add(ARG_PARAM1,mParam1);



        adapter = new AreaAdapter(getContext(),cityInfos);
        mRefreshListView.setAdapter(adapter);










//        final Request request = new Request.Builder()
//                .url("http://123.184.16.19:8008/area/list")
//                .post(builder.build())
//                .build();
//        mOkHttpClient.newCall(request).enqueue(new Callback(){
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                final String res = response.body().string();
//                if (res!=null){
//                    Gson gson = new Gson();
//                    JsonResult jsonResult =  gson.fromJson(res, JsonResult.class);
//                    if (jsonResult.isSuccess()){
//                        List list = (List) jsonResult.getResult();
//
//                        List newList = new ArrayList();
//                        Iterator iterator = list.iterator();
//                        while (iterator.hasNext()){
//                            Map map = (Map) iterator.next();
//                            AreaInfo areaInfo = gson.fromJson(gson.toJson(map),AreaInfo.class);
//                            newList.add(areaInfo);
//                        }
//                        adapter = new AreaAdapter(getContext(),newList);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mRefreshListView.setAdapter(adapter);
//                            }
//                        });
//                    }
//                }
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CityInfo areaInfo = (CityInfo) parent.getAdapter().getItem(position);
        if (areaInfo==null) return;
        if (mListener!=null){
            mListener.onFragmentInteraction(areaInfo);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(CityInfo areaInfo);
    }


    class AreaAdapter extends BaseAdapter {

        private List list;

        private int lastPosition;

        public AreaAdapter(Context context, List<CityInfo> list) {
            this.list = list;
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView==null){
               convertView =  LayoutInflater.from(getContext()).inflate(R.layout.area_list_item,parent,false);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(android.R.id.text1);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            CityInfo item = (CityInfo) list.get(position);
            viewHolder.textView.setText(item.getAreaName());
            if (lastPosition<position&&lastPosition!=0){
                ObjectAnimator.ofFloat(convertView,"translationY",convertView.getHeight()*2,0).setDuration(500).start();
            }
            lastPosition = position;
            return convertView;
        }

        class ViewHolder{
            TextView textView;
        }
    }
}
