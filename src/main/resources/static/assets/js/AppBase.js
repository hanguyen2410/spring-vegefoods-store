class AppBase {
    static DOMAIN_SERVER = location.origin;

    static API_SERVER = this.DOMAIN_SERVER + '/api';

    static API_AUTH = this.API_SERVER + '/auth';

    static API_PRODUCT = this.API_SERVER + '/products';

    static API_CUSTOMER = this.API_SERVER + '/customers';

    static PROVINCE_API = "https://vapi.vnappmob.com/api/province/";

    static DISTRICT_API = this.PROVINCE_API + "district/";

    static WARD_API = this.PROVINCE_API + "ward/";


    static ROLE_API = this.DOMAIN_SERVER + "/api/roles";


    static BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/dghrt3jel/image/upload";
    static SCALE_IMAGE_W100_H80_Q100 = "c_limit,w_100,h_80,q_100";
    static SCALE_IMAGE_W600_H850_Q100 = "c_limit,w_600,h_850,q_100";
    static SCALE_IMAGE_W250_H200_Q100 = "c_limit,w_250,h_200,q_100";
    static SCALE_IMAGE_W200_H250_Q100 = "c_limit,w_200,h_250,q_100";

    static SweetAlert = class {
        static showDeactivateConfirmDialog() {
            return Swal.fire({
                icon: 'warning',
                text: 'Are you sure to deactivate the selected customer ?',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Yes, please deactivate this client !',
                cancelButtonText: 'Cancel',
            })
        }

        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }

        static showError401() {
            Swal.fire({
                icon: 'error',
                title: 'Access Denied',
                text: 'Invalid credentials!',
            })
        }

        static showError403() {
            Swal.fire({
                icon: 'error',
                title: 'Access Denied',
                text: 'Bạn không được phép thực hiện chức năng này!',
            })
        }

        static showError500() {
            Swal.fire({
                icon: 'error',
                title: 'Internal Server Error',
                text: 'Hệ thống Server đang có vấn đề hoặc không truy cập được.',
            })
        }
    }
    static IziToast = class {
        static showSuccessAlertLeft(m) {
            iziToast.success({
                title: 'OK',
                position: 'topLeft',
                timeout: 2500,
                message: m
            });
        }

        static showSuccessAlertRight(m) {
            this.iziToast.success({
                title: 'OK',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }

        static showErrorAlertLeft(m) {
            iziToast.error({
                title: 'Error',
                position: 'topLeft',
                timeout: 2500,
                message: m
            });
        }

        static showErrorAlertRight(m) {
            iziToast.error({
                title: 'Error',
                position: 'topRight',
                timeout: 2500,
                message: m
            });
        }

    }
}
class ProductAvatar {
    constructor(id, fileName, fileFolder, fileUrl, fileType, cloudId, ts) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.cloudId = cloudId;
        this.ts = ts;
    }
}
class Product {
    constructor(id, nameProduct, price, quantity, description, productAvatar) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.productAvatar = productAvatar;
    }
}
class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, wardId, wardName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.wardId = wardId;
        this.wardName = wardName;
        this.address = address;
    }
}

class Role {
    constructor(id, code) {
        this.id = id;
        this.code = code;
    }
}

class User {
    constructor(id, username, password, role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

class StaffAvatar {
    constructor(id, fileName, fileFolder, fileUrl, fileType, cloudId, ts) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.cloudId = cloudId;
        this.ts = ts;
    }
}

class Staff {
    constructor(id, fullName, phone, user, locationRegion, staffAvatar) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.user = user;
        this.locationRegion = locationRegion;
        this.staffAvatar = staffAvatar;
    }
}





class CustomerAvatar {
    constructor(id, fileName, fileFolder, fileUrl, fileType, cloudId, ts) {
        this.id = id;
        this.fileName = fileName;
        this.fileFolder = fileFolder;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.cloudId = cloudId;
        this.ts = ts;
    }
}

class Customer {
    constructor(id, fullName, phone, user, locationRegion, customerAvatar) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.user = user;
        this.locationRegion = locationRegion;
        this.customerAvatar = customerAvatar;
    }
}

