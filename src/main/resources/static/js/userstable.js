// Call the dataTables jQuery plugin
$(document).ready( function() {
    $('#userstable').DataTable();
    loadUsers();
});


async function loadUsers(){
    let allHeaders = getHeaders();
    const request = await fetch('api/users', {
        method: 'GET',
        headers: allHeaders,
    });

    const users = await request.json();



    let usersList = ''
    for(let user of users){
    let deleteButton = `<a onclick='deleteUser(${user.id})' href="#" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>`;


        let userTemplate = `
        <tr class="odd">
        <td class="sorting_${user.id}">${user.id}</td>
        <td>${user.name + ' ' + user.lastName}</td>
        <td>${user.email}</td><td>${user.phone}</td>
        <td> ${deleteButton} </td>
        </tr>`;

        usersList += userTemplate;
    }
    document.querySelector('#userstable tbody').outerHTML = usersList;
}

async function deleteUser(id){

    if(!confirm('Do you want to delete this user?')){
        return;
    }

    let allHeaders = getHeaders();


    const request = await fetch(`api/users/delete/${id}`, {
       method: 'DELETE',
       headers: allHeaders,
    });

    location.reload();

}

function getHeaders(){
    let token = localStorage.getItem('token');
    if(token === undefined || token === null){
        window.location.href = "login.html";
    }
    return {'Accept': 'application/json', 'Content-Type': 'application/json','Authorization': token}
}