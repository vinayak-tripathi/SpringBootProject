const setCookie = (key, val, days) => {
    let date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
    document.cookie = `${key}=${val};expires=${date.toGMTString()}`;
}

async function getFormData(event) {
    event.preventDefault();
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");
    raw = JSON.stringify({
        username: document.getElementById('inputEmail').value,
        password: document.getElementById('inputPassword').value
    })
    console.log(raw)
    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };
    response = await fetch(`http://localhost:8080/auth`, requestOptions)

    if (response.ok) {
        let data = await response.json()
        console.log(data)
        location.replace("/FrontEnd/index.html")
        localStorage.setItem("jwt", data.jwt);
    }
    else {
        console.log(response.status == 200, typeof (response.status))
        let data = await response.json()
        console.log(data)
        alert(data.message);
    }
}

window.onload = function () {
    thisForm = document.getElementById('myForm')
    thisForm.addEventListener('submit', getFormData)
}