// Function to validate the form
function validateForm() {
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const dob = document.getElementById('dob').value;
    const phone = document.getElementById('phone').value;
    
    if (!name || !email || !dob || !phone) {
        alert('All fields are required!');
        return false;
    }
    return true;
}
