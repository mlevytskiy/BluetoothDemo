package demo.bluetooth.com.bluetoothdemo;

import android.bluetooth.BluetoothDevice;
import android.content.*;
import android.content.res.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class DeviceListAdapter extends BaseAdapter{
	private LayoutInflater mInflater;	
	private List<BluetoothDevice> mData;
	private OnPairButtonClickListener mListener;
	
	public DeviceListAdapter(Context context) { 
        mInflater = LayoutInflater.from(context);        
    }
	
	public void setData(List<BluetoothDevice> data) {
		mData = data;
	}
	
	public void setListener(OnPairButtonClickListener listener) {
		mListener = listener;
	}
	
	public int getCount() {
		return (mData == null) ? 0 : mData.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		if (convertView == null) {			
			convertView	=  mInflater.inflate(R.layout.list_item_device, null);
			
			holder = new ViewHolder(convertView);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.fill(mData.get(position), mListener, position);
		
        return convertView;
	}

	static class ViewHolder {

		TextView nameTv;
		TextView addressTv;
		TextView pairBtn;
		private Resources resources;

		public ViewHolder(View view) {
			nameTv = (TextView) view.findViewById(R.id.tv_name);
			addressTv = (TextView) view.findViewById(R.id.tv_address);
			pairBtn = (Button) view.findViewById(R.id.btn_pair);
			resources = view.getResources();
		}

		public void fill(BluetoothDevice device, final OnPairButtonClickListener listener, final int position) {
			nameTv.setText(device.getName());
			addressTv.setText(device.getAddress());
			pairBtn.setText((device.getBondState() == BluetoothDevice.BOND_BONDED) ? resources.getString(R.string.unpair) :
					resources.getString(R.string.pair));
			pairBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (listener != null) {
						listener.onPairButtonClick(position);
					}
				}
			});
		}
	}
	
	public interface OnPairButtonClickListener {
		public abstract void onPairButtonClick(int position);
	}
}