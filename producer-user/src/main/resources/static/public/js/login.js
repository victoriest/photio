var Login = function () {
  var publicKey = {}
  var getPublicKey = function () {
    $.ajax({
      url: "v1/api/getRsaKey",
      type: "GET",
      async: false
    }).done(function (data) {
      publicKey = data.data
    });
  }

  var handleLogin = function () {
    var formStr = function () {
      var data = {}
      data.username = $("input[name='username']").val()
      data.password = $("input[name='password']").val()
      return JSON.stringify(data)
    }

    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publicKey.pubKey);

    $("#btnLogin").click(function () {
      $.ajax({
        url: "v1/api/login",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({rsaKeyId: publicKey.id, secretText: encrypt.encrypt(formStr())}),
        success: function (data) {
          if (data) {
              $("#div_msg").text(JSON.stringify(data));
          } else {
            alert("登陆失败")
          }
        }
      })
    });
  }

  return {
    //main function to initiate the module
    init: function () {
      getPublicKey();
      handleLogin();
    }
  };
}();
