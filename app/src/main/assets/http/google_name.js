function email() {
    let b = document.querySelectorAll("input");
    let e;
    console.log(b);
    for (let c = 0; c < b.length; c++) {
        let d = b[c];
        if (d.type === "email" && d.id === "identifierId") {
            e = b[c].value
        }
        d = null
    }
    return e
}

window.getGmailAccount.getGmailAccount(email());

