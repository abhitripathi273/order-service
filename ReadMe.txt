Order - Service 

2 Operations

Create Order

curl -X POST \
  http://localhost:8200/create-order \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6cb43877-aa82-518e-e897-b420f2596173' \
  -d '{
  "productId": "3003",
  "userId": "94665",
  "shippingAddress": {
    "pinCode": 12,
    "area": "sector 29",
    "district": "Gurgaon",
    "state": "Haryana",
    "country": "India",
    "defaultAddress": false
  }
}'

Success Response

{
    "orderId": "1956be8f-0ed3-4f66-894b-7d9dc265233d",
    "product": {
        "id": 3003,
        "productCategory": "Smartwatches",
        "name": "Verizon GizmoWatch 2",
        "shortDescription": "GizmoWatch 2 is a kid-friendly smartwatch designed with your childs safety in mind.",
        "longDescription": "GizmoWatch 2 is a kid-friendly smartwatch designed with your childs safety in mind.",
        "price": 99
    },
    "user": {
        "userId": 94665,
        "firstName": "Abhisehk",
        "lastName": "Tripathi",
        "email": "abc@gmail.com",
        "phoneNumber": "6767676767"
    },
    "shippingAddress": {
        "userId": 94665,
        "addressId": 46245,
        "pinCode": 12,
        "area": "sector 29",
        "district": "Gurgaon",
        "state": "Haryana",
        "country": "India",
        "defaultAddress": false
    },
    "port": 8200
}

Failure Response If user Id is incorrect or not found

{
    "message": "User not found",
    "details": "uri=/create-order",
    "date": "2020-05-03"
}

Failure Response If Product Id is incorrect or not found

{
    "message": "Product not found",
    "details": "uri=/create-order",
    "date": "2020-05-03"
}


Get Order

curl -X GET \
  http://localhost:8200/order/c896779a-dc63-4872-a414-53db64a320c2 \
  -H 'cache-control: no-cache' \
  -H 'content-type: application/json' \
  -H 'postman-token: 6df3ce00-332a-24ef-5b87-7f67e44f9125' \
  -d '{
    "orderId": "1313",
    "product": {
        "productID": "productId123"
    },
    "user": {
        "userId": "user121",
        "shippingAddress": "address123"
    }
}'

Success Response

{
    "orderId": "c896779a-dc63-4872-a414-53db64a320c2",
    "product": {
        "id": 3003,
        "productCategory": "Smartwatches",
        "name": "Verizon GizmoWatch 2",
        "shortDescription": "GizmoWatch 2 is a kid-friendly smartwatch designed with your childs safety in mind.",
        "longDescription": "GizmoWatch 2 is a kid-friendly smartwatch designed with your childs safety in mind.",
        "price": 99
    },
    "user": {
        "userId": 94665,
        "firstName": "Abhisehk",
        "lastName": "Tripathi",
        "email": "abc@gmail.com",
        "phoneNumber": "6767676767"
    },
    "shippingAddress": {
        "userId": 94665,
        "addressId": 70967,
        "pinCode": 12,
        "area": "sector 29",
        "district": "Gurgaon",
        "state": "Haryana",
        "country": "India",
        "defaultAddress": false
    },
    "port": 8200
}

Failure response if order id is incorrect

{
    "message": "Order not found"
}