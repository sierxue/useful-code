package osc.git.eh3.testopen;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import osc.git.eh3.utils.AESTool;
import osc.git.eh3.utils.Base64;
import osc.git.eh3.utils.HttpClientUtil;

public class TestOpenPuton {
	public static String URL = "http://127.0.0.1:3/dsp-open/opendsp.do";
	private static String key = "adjdjfjfjfjdkdkd";//
	private static String appid = "t123456";// 用户
	private static String token = "cst123456";// 令牌

	public static void main(String[] args) throws Exception {

		String sendPostParam = HttpClientUtil.sendPostParam(URL, getPostParam("getKpiByCampaignIds"));// 获得数据并且发送请求
		String data = getData(sendPostParam);
		System.out.println(JSONObject.fromObject(data));
	}

	// 解密
	public static String getData(String encryptString) throws Exception {
		byte[] decode = Base64.decode(encryptString.getBytes());
		String aString = new String(decode, "utf-8");
		String decrypt = AESTool.decrypt(aString, key);
		return decrypt;
	}

	public static Map<String, String> getPostParam(String content) throws Exception {
		Map<String, String> postParam = new HashMap<String, String>();
		content = getContent(content);

		// 业务数据
		long millis = System.currentTimeMillis();// 时间戳j
		content = AESTool.encrypt(content, key);// 使用aes加密
		String lol = SignatureUtil.digest(content, "MD5");// 摘要
		String signature = SignatureUtil.generateSignature(appid, token, lol, millis);// 签名

		// 准备提交数据
		postParam.put("appid", appid);
		postParam.put("content", content);
		postParam.put("lol", lol);
		postParam.put("signature", signature);
		postParam.put("millis", millis + "");

		return postParam;
	}

	// 在这里写请求数据
	public static String getContent(String contentName) {
		JSONObject content = new JSONObject();
		JSONObject param = new JSONObject();
		content.put("servicename", "putonServiceCall");

		switch (contentName) {
		case "putOnByCreative":
			param.put("campaignid", "26861f62-5cd7-4073-9186-676f8f5d7b24");
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			
			JSONArray mapIds = new JSONArray();
			mapIds.add("28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			mapIds.add("8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			mapIds.add("b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			param.put("mapids", mapIds);

			content.put("funcname", "putOnByCreative");
			content.put("methodparam", param);
			break;
		case "pauseByCreative":
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			
			mapIds = new JSONArray();
			mapIds.add("28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			mapIds.add("8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			mapIds.add("b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			param.put("mapids", mapIds);
			
			content.put("funcname", "pauseByCreative");
			content.put("methodparam", param);
			break;
		case "putOnByAdx":
			param.put("campaignid", "26861f62-5cd7-4073-9186-676f8f5d7b24");
			
			JSONArray groupAdxs = new JSONArray();
			JSONObject groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "6246ae47-d24b-4afa-88ba-57417ccab6aa");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "ce579246-e707-4cb9-b982-88cad7944b92");
			groupAdxs.add(groupAdx);
			
			param.put("groupadxs", groupAdxs);
			
			content.put("funcname", "putOnByAdx");
			content.put("methodparam", param);
			break;
		case "pauseByAdx":
			groupAdxs = new JSONArray();
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "6246ae47-d24b-4afa-88ba-57417ccab6aa");
			groupAdxs.add(groupAdx);
			groupAdx = new JSONObject();
			groupAdx.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			groupAdx.put("adxid", "ce579246-e707-4cb9-b982-88cad7944b92");
			groupAdxs.add(groupAdx);
			
			param.put("groupadxs", groupAdxs);
			
			content.put("funcname", "pauseByAdx");
			content.put("methodparam", param);
			break;
		case "putOnByGroup":
			param.put("campaignid", "26861f62-5cd7-4073-9186-676f8f5d7b24");
			
			JSONArray groupids = new JSONArray();
			groupids.add("022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("groupids", groupids);

			content.put("funcname", "putOnByGroup");
			content.put("methodparam", param);
			break;
		case "pauseByGroup":
			groupids = new JSONArray();
			groupids.add("022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("groupids", groupids);
			
			content.put("funcname", "pauseByGroup");
			content.put("methodparam", param);
			break;
		case "putOnByCampaign":
			JSONArray campaignids = new JSONArray();
			campaignids.add("26861f62-5cd7-4073-9186-676f8f5d7b24");
			param.put("campaignids", campaignids);
			
			content.put("funcname", "putOnByCampaign");
			content.put("methodparam", param);
			break;
		case "pauseByCampaign":
			campaignids = new JSONArray();
			campaignids.add("26861f62-5cd7-4073-9186-676f8f5d7b24");
			param.put("campaignids", campaignids);
			
			content.put("funcname", "pauseByCampaign");
			content.put("methodparam", param);
			break;
		case "setAdxProp":
			JSONArray propdatas = new JSONArray();
			JSONObject propdata = new JSONObject();
			JSONObject adxprop = new JSONObject();
			JSONArray adxprops = new JSONArray();
			
			adxprop.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			adxprop.put("prop", 40);
			adxprops.add(adxprop);
			
			adxprop = new JSONObject();
			adxprop.put("adxid", "6246ae47-d24b-4afa-88ba-57417ccab6aa");
			adxprop.put("prop", 30);
			adxprops.add(adxprop);
			
			adxprop = new JSONObject();
			adxprop.put("adxid", "ce579246-e707-4cb9-b982-88cad7944b92");
			adxprop.put("prop", 30);
			adxprops.add(adxprop);
			
			propdata.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			propdata.put("adxprops", adxprops);
			
			propdatas.add(propdata);
			
			param.put("propdatas", propdatas);
			content.put("funcname", "setAdxProp");
			content.put("methodparam", param);
			break;
		case "setCreateivePrice":
			JSONArray createiveprices = new JSONArray();
			
			JSONObject createiveprice = new JSONObject();
			createiveprice.put("mapid", "28f13909-dbbe-42e4-b9fd-edd97a31d6ce");
			createiveprice.put("price", 10);
			createiveprices.add(createiveprice);
			
			createiveprice = new JSONObject();
			createiveprice.put("mapid", "8b7b1b4a-eb3a-4be0-809b-b497c58a14f6");
			createiveprice.put("price", 6);
			createiveprices.add(createiveprice);
			
			createiveprice = new JSONObject();
			createiveprice.put("mapid", "b7f39e0c-3025-4fa3-8e83-ef1f492fe358");
			createiveprice.put("price", 8);
			createiveprices.add(createiveprice);
			
			param.put("groupid", "022ea1a5-3f21-40dd-9c24-c0edfa82bfda");
			param.put("adxid", "1fed4171-9925-4834-aa7b-9b4d3a58841b");
			param.put("createiveprices", createiveprices);
			
			content.put("funcname", "setCreateivePrice");
			content.put("methodparam", param);
			break;
		case "getKpiByCampaignIds":
			campaignids = new JSONArray();
			campaignids.add("26861f62-5cd7-4073-9186-676f8f5d7b24");
			
			param.put("campaignids", campaignids);
			
			content.put("funcname", "getKpiByCampaignIds");
			content.put("methodparam", param);
			break;
		default:
			break;
		}
		System.out.println(content.toString());
		return content.toString();
	}
}
