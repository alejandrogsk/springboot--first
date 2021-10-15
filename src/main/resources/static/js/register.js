// Call the dataTables jQuery plugin
$(document).ready( function() {
    //
});


async function createUser(){
    let data = {};
    data.name = document.getElementById('rName').value;
    data.last_name = document.getElementById('rLastName').value;
    data.email = document.getElementById('rEmail').value;
    data.phone = document.getElementById('rPhone').value;
    data.password = document.getElementById('rPassword').value;

    const request = await fetch(`api/users`, {
       method: 'POST',
       headers: {
           'Accept': 'application/json',
           'Content-Type': 'application/json'
       },
       body: JSON.stringify(data)
    });

    alert("Account created successfully")
    window.location.href = "users.html";
}