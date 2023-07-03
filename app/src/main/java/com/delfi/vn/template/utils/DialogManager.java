package com.delfi.vn.template.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.delfi.vn.template.R;
import com.delfi.vn.template.utils.printer.utils.BTDeviceAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DialogManager {
	public static void showBluetoothDialog(Context context, final Set<BluetoothDevice> pairedDevices) {
		final String[] items = new String[pairedDevices.size()];
		int index = 0;
		for (BluetoothDevice device : pairedDevices) {
			items[index++] = device.getName() + "\n" + device.getAddress();
		}
		Dialog dialog = new Dialog(context);
		dialog.setTitle("Paired Bluetooth printers");
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(View.inflate(context, R.layout.dialog_choose_device, null));
		RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);

		recyclerView.setLayoutManager(new LinearLayoutManager(context));
		BTDeviceAdapter adapter = new BTDeviceAdapter();
		adapter.setDataList((List<BluetoothDevice>) pairedDevices);
		adapter.setListener(item -> {
			dialog.dismiss();
			//MainActivity.mBixolonLabelPrinter.connect(strDeviceInfo);
		});
		recyclerView.setAdapter(adapter);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(true);
		dialog.show();
	}

	public static void showNetworkDialog(Context context, String list) {
		if (list != null && list.length()>0) {
			try {
				if (list != null && list.length() > 0) {
					int j = 0;
					for (int i = list.length() - 1; i >= 0; i--) {
						char ch = list.charAt(i);
						if (ch == '{') {
							String pre = list.substring(0, i);
							String post = list.substring(i);
							String ins = "printer" + j + ":";
							list = pre + ins + post;
							j++;
						} else {
							String pre = list.substring(0, i);
							String post = list.substring(i);
							String ins = "printer" + j + ":";
						}
					}
				}
				list = "{" + list + "}";
				JSONObject jsonObject = new JSONObject(list);
				Iterator<String> tempGroupKey = jsonObject.keys();


				int i = 0;
				String address = "", port = "";
				final String[] items = new String[jsonObject.length()];

				while (tempGroupKey.hasNext()) {
					String grpKey = tempGroupKey.next();

					JSONObject obj = new JSONObject(jsonObject.get(grpKey).toString());
					Iterator<String> tempChildKey = obj.keys();
					while (tempChildKey.hasNext()) {
						String key = tempChildKey.next();
						if (key.equals("address")) {
							address = obj.getString(key);
						} else if (key.equals("portNumber")) {
							port = obj.getString(key);

							items[i++] = address + ":" + port;
						}
					}
				}
				new AlertDialog.Builder(context).setTitle("Connectable network printers")
						.setItems(items, new OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								//MainActivity.mBixolonLabelPrinter.connect(items[which].split(":")[0], Integer.valueOf(items[which].split(":")[1]), 5000);
							}
						}).show();
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
	}
}
