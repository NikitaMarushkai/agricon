$.fn.serializeObject = function() {
  var o = {};
  var a = this.serializeArray();
  $.each(a, function() {
    if (o[this.name]) {
      if (!o[this.name].push) {
        o[this.name] = [o[this.name]];
      }
      o[this.name].push(this.value || '');
    } else {
      o[this.name] = this.value || '';
    }
  });
  return o;
};


function showSpinner() {
  $("body").loadingModal({
    text: 'Please wait, your request is being processed... ',
    opacity: '0.9'
  });
  $("body").loadingModal('show');
}

function hideSpinner() {
  $("body").loadingModal('hide');

}

jQuery(document).ready(function($) {
  "use strict";

  //Contact
  $('form.contactForm').submit(function() {
    var f = $(this).find('.form-group'),
      ferror = false,
      emailExp = /^[^\s()<>@,;:\/]+@\w[\w\.-]+\.[a-z]{2,}$/i,
      phoneExp = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;


    f.children('input').each(function() { // run all inputs

      var i = $(this); // current input
      var rule = i.attr('data-rule');

      if (rule !== undefined) {
        var ierror = false; // error flag for current input
        var pos = rule.indexOf(':', 0);
        if (pos >= 0) {
          var exp = rule.substr(pos + 1, rule.length);
          rule = rule.substr(0, pos);
        } else {
          rule = rule.substr(pos + 1, rule.length);
        }

        switch (rule) {
          case 'required':
            if (i.val() === '') {
              ferror = ierror = true;
            }
            break;

          case 'minlen':
            if (i.val().length < parseInt(exp)) {
              ferror = ierror = true;
            }
            break;

          case 'email':
            if (!emailExp.test(i.val())) {
              ferror = ierror = true;
            }
            break;

          case 'phone':
            if (!phoneExp.test(i.val())) {
              ferror = ierror = true
            }
            break;
            
          case 'checked':
            if (! i.is(':checked')) {
              ferror = ierror = true;
            }
            break;

          case 'regexp':
            exp = new RegExp(exp);
            if (!exp.test(i.val())) {
              ferror = ierror = true;
            }
            break;
        }
        i.next('.validation').html((ierror ? (i.attr('data-msg') !== undefined ? i.attr('data-msg') : 'wrong Input') : '')).show('blind');
      }
    });
    f.children('textarea').each(function() { // run all inputs

      var i = $(this); // current input
      var rule = i.attr('data-rule');

      if (rule !== undefined) {
        var ierror = false; // error flag for current input
        var pos = rule.indexOf(':', 0);
        if (pos >= 0) {
          var exp = rule.substr(pos + 1, rule.length);
          rule = rule.substr(0, pos);
        } else {
          rule = rule.substr(pos + 1, rule.length);
        }

        switch (rule) {
          case 'required':
            if (i.val() === '') {
              ferror = ierror = true;
            }
            break;

          case 'minlen':
            if (i.val().length < parseInt(exp)) {
              ferror = ierror = true;
            }
            break;
        }
        i.next('.validation').html((ierror ? (i.attr('data-msg') != undefined ? i.attr('data-msg') : 'wrong Input') : '')).show('blind');
      }
    });
    if (ferror) return false;
    else var str = $(this).serializeObject();
    var action = $(this).attr('action');
    if( ! action ) {
      action = 'contactform/contactform.php';
    }
    grecaptcha.ready(function() {
      // do request for recaptcha token
      // response is promise with passed token
      grecaptcha.execute('6Lf0xUkbAAAAAG-oFr1LloawlDuXWV3MksgrF6Ih', {action: action}).then(function(token) {
        // add token to form
        str.token = token;
        showSpinner();
        $.ajax({
          type: "POST",
          url: action,
          data: str,
          success: function(msg) {
            // alert(msg);
            if (msg == 'OK') {
              $("#sendmessage").addClass("show");
              $("#errormessage").removeClass("show");
              $('.contactForm').find("input[type != 'hidden'], textarea").val("");
            } else {
              $("#sendmessage").removeClass("show");
              $("#errormessage").addClass("show");
              $('#errormessage').html(msg);
            }
            hideSpinner()
          }
        });
      });
    });
    return false;
  });

});
