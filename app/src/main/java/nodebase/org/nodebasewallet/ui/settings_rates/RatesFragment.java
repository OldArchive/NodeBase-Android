package nodebase.org.nodebasewallet.ui.settings_rates;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import nodebase.org.nodebasewallet.R;
import global.NodeBaseRate;
import nodebase.org.nodebasewallet.ui.base.BaseRecyclerFragment;
import nodebase.org.nodebasewallet.ui.base.tools.adapter.BaseRecyclerAdapter;
import nodebase.org.nodebasewallet.ui.base.tools.adapter.BaseRecyclerViewHolder;
import nodebase.org.nodebasewallet.ui.base.tools.adapter.ListItemListeners;

/**
 * Created by akshaynexus on 7/2/17.
 */

public class RatesFragment extends BaseRecyclerFragment<NodeBaseRate> implements ListItemListeners<NodeBaseRate> {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setEmptyText("No rate available");
        setEmptyTextColor(Color.parseColor("#cccccc"));
        return view;
    }

    @Override
    protected List<NodeBaseRate> onLoading() {
        return nodebaseModule.listRates();
    }

    @Override
    protected BaseRecyclerAdapter<NodeBaseRate, ? extends NodeBaseRateHolder> initAdapter() {
        BaseRecyclerAdapter<NodeBaseRate, NodeBaseRateHolder> adapter = new BaseRecyclerAdapter<NodeBaseRate, NodeBaseRateHolder>(getActivity()) {
            @Override
            protected NodeBaseRateHolder createHolder(View itemView, int type) {
                return new NodeBaseRateHolder(itemView,type);
            }

            @Override
            protected int getCardViewResource(int type) {
                return R.layout.rate_row;
            }

            @Override
            protected void bindHolder(NodeBaseRateHolder holder, NodeBaseRate data, int position) {
                holder.txt_name.setText(data.getCode());
                if (list.get(0).getCode().equals(data.getCode()))
                    holder.view_line.setVisibility(View.GONE);
            }
        };
        adapter.setListEventListener(this);
        return adapter;
    }

    @Override
    public void onItemClickListener(NodeBaseRate data, int position) {
        nodebaseApplication.getAppConf().setSelectedRateCoin(data.getCode());
        Toast.makeText(getActivity(),R.string.rate_selected,Toast.LENGTH_SHORT).show();
        getActivity().onBackPressed();
    }

    @Override
    public void onLongItemClickListener(NodeBaseRate data, int position) {

    }

    private  class NodeBaseRateHolder extends BaseRecyclerViewHolder{

        private TextView txt_name;
        private View view_line;

        protected NodeBaseRateHolder(View itemView, int holderType) {
            super(itemView, holderType);
            txt_name = (TextView) itemView.findViewById(R.id.txt_name);
            view_line = itemView.findViewById(R.id.view_line);
        }
    }
}
