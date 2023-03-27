const logout = () => {
    localStorage.clear();
}
function isLogin() {
    let auth = localStorage.getItem("jwt");
    if (auth == null) {
        console.log('Not AUthenticated')
    }
    else {
        document.getElementById("isLogin").innerHTML = 'Profile';
        document.getElementById("isLogin").setAttribute('href', 'profile.html')
    }
}