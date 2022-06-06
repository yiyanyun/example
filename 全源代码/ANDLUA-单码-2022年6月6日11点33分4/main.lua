require "import"
import "android.app.*"
import "android.os.*"
import "android.widget.*"
import "android.view.*"
import "layout"
import "AndLua"
json=import "cjson"
import "android.util.Base64"
import "java.lang.*"
import "android.util.*"
import "android.content.*"
activity.setTheme(R.Theme_Blue)
activity.setTitle("易验云")
activity.setContentView(loadlayout(layout))

沉浸状态栏()
隐藏标题栏()
--先导入包

--[[
本源码仅限于学习使用，严禁其他原图。
其次请注意中部的那些配置选项里的值。
]]

function encode(data)--编码
  local Base64=luajava.bindClass("android.util.Base64")
  return Base64.encodeToString(String(data).getBytes(),Base64.NO_WRAP);
end

function decode(data)--解码
  local Base64=luajava.bindClass("android.util.Base64")
  return String(Base64.decode(data,Base64.DEFAULT)).toString()
end

--下面是获取设备码
import "java.io.File"--导入File类
机器码路径="/storage/emulated/0/.imeis"
isfile=File(机器码路径).exists()
if isfile==false then
  imei=math.random(10000000000,999999999999)..os.time()
  io.open(机器码路径,"w"):write(imei):close()
end
imei=io.open(机器码路径):read("*a")
if imei=="" or #imei<15 then
  imei=math.random(10000000000,999999999999)..os.time()
  io.open(机器码路径,"w"):write(imei):close()
end

获取设备码=io.open(机器码路径):read("*a")

--这个是云端接口
api = "http://172.17.204.10/api/"
--下面的是对接码不要填写/ 其次要与后台的操作对应
kamilogin = "HCGZOJoN0z"

notice = "3h4uJl6UtL"
--浏览器注册链接
url = "http://www.baidu.com"






function login.onClick()
  local card = card.text
  param = "scode="..card.."&mac="..获取设备码
  param = "data="..encode(param)
  Http.post(api..kamilogin,param,nil,nil,function(code,bb)
    if code == 200 then
      content = decode(bb)
      aa=json.decode(content)
      if aa.code == 200 then
        MD提示("验证成功",0xFF2196F3,0xFFFFFFFF,4,10)
       else
        MD提示(aa.explain,0xFF2196F3,0xFFFFFFFF,4,10)
      end
     else
      Toast.makeText(activity, "服务器连接失败，状态码："..code,Toast.LENGTH_SHORT).show()
    end
  end)
end


--获取项目公告
param = "time="..os.time()
param = "data="..encode(param)
Http.post(api..notice,param,nil,nil,function(code,bb)
  if code == 200 then
    content = decode(bb)
    aa=json.decode(content)
    if aa.code == 200 then
      dialog=AlertDialog.Builder(this)
      .setTitle("云公告")
      .setMessage(content)
      .setPositiveButton("知道了",{onClick=function(v) print"点击了积极按钮"end})
      .show()
      dialog.create()

      --更改消息颜色
      message=dialog.findViewById(android.R.id.message)
      message.setTextColor(0xff1DA6DD)

      --更改Button颜色
      import "android.graphics.Color"
      dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(0xff1DA6DD)
      dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(0xff1DA6DD)
      dialog.getButton(dialog.BUTTON_NEUTRAL).setTextColor(0xff1DA6DD)

      --更改Title颜色
      import "android.text.SpannableString"
      import "android.text.style.ForegroundColorSpan"
      import "android.text.Spannable"
      sp = SpannableString("应用公告")
      sp.setSpan(ForegroundColorSpan(0xff1DA6DD),0,#sp,Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
      dialog.setTitle(sp)

      MD提示("获取成功",0xFF2196F3,0xFFFFFFFF,4,10)
     else
      MD提示(aa.explain,0xFF2196F3,0xFFFFFFFF,4,10)
    end
   else
    Toast.makeText(activity, "服务器连接失败",Toast.LENGTH_SHORT).show()
  end
end)


function llq.onClick()
  import "android.content.Intent"
  import "android.net.Uri"
  viewIntent = Intent("android.intent.action.VIEW",Uri.parse(url))
  activity.startActivity(viewIntent)
end
