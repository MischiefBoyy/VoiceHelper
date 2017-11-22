function AppCommon(){
};
AppCommon.prototype={
  init:function(){
  },
  /**
   * 
   * @param 执行语句
   * @returns
   */
  query:function(appUrl,data){
    
    var retStr=null;
    retStr=ajaxPost(appUrl,data);
    if(retStr.state==RET_OK){
      return retStr;
    }else{
      //alert("获取失败: "+retStr.data);
      checkLogin(retStr.data[0]);
      return false;
    }
  },
  /**
   * 
   * @param 获取List
   * @returns
   */
  querylist:function(appUrl,data){
    var retStr=null;
    retStr=ajaxPost(appUrl,data);
    if(retStr==null){
      alert('获取失败: '+appUrl);
      return false;
    }
    if(retStr.state==RET_OK){
      return retStr.data;
    }else{
      alert('获取失败: '+retStr.data);
      checkLogin(retStr.data);
      return false;
    }
  },
  endF:function(){
  }
};
function checkLogin(data){
  if(data=='请先登录!'){
	  alert("登录超时,请重新登录!");
    top.location.href='../html/login.html';
  }
}
// 用于解析location.search
function parseQuery(search){
  var args=search.substring(1).split('&');
  var argsParsed={};
  var i,arg,kvp,key,value;
  for(i=0;i<args.length;i++){
    arg=args[i];
    if(-1===arg.indexOf('=')){
      argsParsed[decodeURIComponent(arg).trim()]=true;
    }else{
      kvp=arg.split('=');
      key=decodeURIComponent(kvp[0]).trim();
      value=decodeURIComponent(kvp[1]).trim();
      argsParsed[key]=value;
    }
  }
  return argsParsed;
}
function ajaxPost(appUrl){
  return ajaxPost(appUrl,'');
}
function ajaxPost(appUrl,data){
  var retStr=null;
  $.ajax({
    async:false,
    type:'POST',
    data:data,
    url:appUrl,
    contentType:'application/x-www-form-urlencoded;charset=utf-8',
    success:function(retText){
      retStr=retText;
    }
  });
  return $.parseJSON(retStr);
}
function ajaxGetPage(appUrl){
  var ret=null;
  $.ajax({
    async:false,
    type:'GET',
    cache:false,
    url:appUrl,
    contentType:'charset=utf-8',
    success:function(retText){
      ret=retText;
    }
  });
  return ret;
}
/**
 * 返回yyyy-mm-dd
 * 
 * @returns
 */
function getDate(){
  var d=new Date();
  var dStr=[d.getFullYear(),d.getMonth()+1,d.getDate()].join('-');
  return dStr;
}
/**
 * 
 * @param 表单转换Json
 * @returns
 */
function form2Json(id){
  var arr=$('#'+id).serializeArray();
  var jsonStr='';
  jsonStr+='{';
  for(var i=0;i<arr.length;i++){
    jsonStr+='"'+arr[i].name+'":"'+arr[i].value+'",';
  }
  jsonStr=jsonStr.substring(0,(jsonStr.length-1));
  jsonStr+='}';
  var json=JSON.parse(jsonStr);
  return json;
}
/**
 * 
 * @param 获取登录信息
 * @returns
 */
function loginInfo() {
    var appUrl = window.PRJ_ROOT
    + '/rootServlet.do?menuCode=TestMng&processType=com.inrich.pjrecord.action.Login&actionType=loginInfo';
      var data = new AppCommon().query(appUrl,'');
      if(!data){
        return null;
      }
      
      console.log(data.data);
      return data.data;
  }

function trimStr(str){return str.replace(/(^\s*)|(\s*$)/g,"");}

function loadMapJScript() {
  var script = document.createElement("script");
  script.type = "text/javascript";
  script.src = "http://api.map.baidu.com/api?v=2.0&ak=G132V11oPFaPLT5c8LQoVcObhvRdnaE4&callback=initMap";
  document.body.appendChild(script);
}
function initMap() {
  console.log("map");
}