use ParkingLotManagement
db.createCollection("discounts")
db.createCollection("prices")
db.getCollection("discounts").remove({})
db.getCollection("prices").remove({})

db.getCollection("discounts").insertMany
([
    {
        percent: "0.25",
        userType: "REGULAR"
    },
    
    { 
        percent: "0.50",
        userType: "VIP"
    }
])

db.getCollection("prices").insertMany
([
    {
        typeInfo : {
            typeName: "parkingSpot",
            typeValue: "SMALL"
        },
        price : {
            units : 0.1,
            currency : "EUR"
        }
    },
    
    {
        typeInfo : {
            typeName: "parkingSpot",
            typeValue: "MEDIUM"
        },
        price : {
            units : 0.2,
            currency : "EUR"
        }
    },
    
    {
        typeInfo: {
            typeName: "parkingSpot",
            typeValue: "LARGE"
        },
        price: {
            units: 0.3,
            currency: "EUR"
        }
    },
    
    {
        typeInfo: {
            typeName: "user",
            typeValue: "REGULAR"
        },
        price: {
            units: 0.5,
            currency: "EUR"
        }
    },
    
    {
        typeInfo: {
            typeName: "user",
            typeValue: "VIP"
        },
        price: {
            units: 1.0,
            currency: "EUR"
        }
    },
    
    {
        typeInfo: {
            typeName: "vehicle",
            typeValue: "MOTORCYCLE"
        },
        price: {
            units: 0.5,
            currency: "EUR"
        }
    },
    
    {
        typeInfo: {
            typeName: "vehicle",
            typeValue: "CAR"
        },
        price: {
            units: 0.15,
            currency: "EUR"
        }
    },
    
    {
        typeInfo: {
            typeName: "vehicle",
            typeValue: "TRUCK"
        },
        price: {
            units: 0.2,
            currency: "EUR"
        }
    }
])