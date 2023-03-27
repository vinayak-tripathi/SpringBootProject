function hideloader() {
    document.getElementById('loading').style.display = 'none';
}

async function getProductDetailforEditing(id) {
    var myHeaders = new Headers();
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };
    response = await fetch(`http://localhost:8080/api/products/${id}`, requestOptions)
    if (response.ok) {
        let data = await response.json()
        document.getElementById('titleE').innerHTML = `Edit-${data.name}`
        document.getElementById('productId').value = data.id
        document.getElementById('productNameE').value = data.name
        document.getElementById("categoryE").value = data.category
        document.getElementById("priceE").value = data.price
        document.getElementById("ratingE").value = data.rating
        document.getElementById("descE").value = data.description
        document.getElementById('quantityE').value = data.quantity
        console.log(data)
    }
}

async function deleteProduct(id) {
    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${localStorage.getItem('jwt')}`);

    var requestOptions = {
        method: 'DELETE',
        headers: myHeaders,
        redirect: 'follow'
    };
    var result = confirm("Want to delete?");
    if (result) {

        response = await fetch(`http://localhost:8080/api/products/${id}`, requestOptions)
        if (response.ok) {
            console.log('bhai Delete kar diya')
            let data = await response.text();
            // alert(data);
            location.reload();
            return;
        }
    }
    alert('Action Cancelled');
    let data = await response.json()
}



async function isAdmin() {
    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${localStorage.getItem('jwt')}`);
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };
    let response = await fetch("http://localhost:8080/profile", requestOptions);
    if (response.ok) {
        let vt = await response.json();
        if (vt.isadmin == 0) {
            return false
        }
        else {
            return true
        }
    }

}
async function getSpecificProduct(id) {
    var myHeaders = new Headers();
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };
    response = await fetch(`http://localhost:8080/api/products/${id}`, requestOptions)
    if (response) {
        Array.from(document.getElementsByClassName('spinner-border')).forEach((item, index) => {
            item.style.display = 'none'
        })
    }
    let data = await response.json()
    document.getElementById('title').innerHTML = data.name
    document.getElementById("category").innerHTML = data.category
    document.getElementById("price").innerHTML = `Price: ${data.price}`
    document.getElementById("rating").innerHTML = `Ratings: ${data.rating}`
    document.getElementById("desc").innerHTML = data.description
    document.getElementById('quantity').innerHTML = `Quantity: ${data.quantity}`
    console.log(data)
}

async function getProducts() {
    var myHeaders = new Headers();
    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };
    response = await fetch("http://localhost:8080/api/products", requestOptions)
    if (response) {
        hideloader();
        console.log(response.status)
    }
    let data = await response.json()
    const isadmin = await isAdmin()
    showProducts(data, isadmin)
}

function showProducts(data, isadmin) {
    let tab = ""

    if (isadmin) {
        for (let r of data) {
            tab += `
            <div class="col-sm-4 mt-3 mb-3"><div class="card" style="width: 18rem;">
                <div class="shadow card-body">
                    <h5 class="card-title">${r.name}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${r.category}</h6>
                    <p class="card-text">
                        Price: ${r.price}<br>
                        Rating: ${r.rating}
                    </p>
                    <button type="button" class="btn btn-primary m-1" data-bs-toggle="modal" data-bs-target="#exampleModalCenter" onclick="getSpecificProduct(${r.id})">
                        See Description
                    </button>
                    <button type="button" class="btn btn-warning m-1" data-bs-toggle="modal" data-bs-target="#editModal" onclick="getProductDetailforEditing(${r.id})">
                        Edit
                    </button>
                    <button type="button" class="btn btn-danger m-1" onclick="deleteProduct(${r.id})">
                        Delete
                    </button>
                </div>
            </div>
            </div>`;
        }
    }
    else {
        for (let r of data) {
            tab += `
            <div class="col-sm-4 mt-3 mb-3"><div class="card" style="width: 18rem;">
                <div class="shadow card-body">
                    <h5 class="card-title">${r.name}</h5>
                    <h6 class="card-subtitle mb-2 text-muted">${r.category}</h6>
                    <p class="card-text">
                        Price: ${r.price}<br>
                        Rating: ${r.rating}
                    </p>
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModalCenter" onclick="getSpecificProduct(${r.id})">
                        See Description
                    </button>
                </div>
            </div>
            </div>`;
        }
    }
    document.getElementById("employees").innerHTML = tab;
}

window.onload = getProducts()




