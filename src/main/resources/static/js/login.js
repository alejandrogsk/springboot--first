async function loginUser(){
    let data = {};
    data.email = document.getElementById('lEmail').value;
    data.password = document.getElementById('lPassword').value;

    const req = await fetch("api/login", {
       method: 'POST',
       body: JSON.stringify(data),
       headers: {
           'Accept': 'application/json',
           'Content-Type': 'application/json'
       }
    });

    const res = await req.text();
    console.log(res);
    if(res !== "fine"){
        localStorage.setItem('token', res);
        window.location.href = "users.html";
    } else {
        alert("Your credential are wrong, please try again");
    }

}