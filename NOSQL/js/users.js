db.users.insert(
    [
        {
            "idUser": 1,
            "username": "siderjonas",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "soldanochristian@hotmail.com",
            "name": "Christian",
            "lastname": "Soldano",
            "dni": 40020327,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "Manuel Acevedo 2685",
            "role": "ADMIN",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 1,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "6363325",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 87,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "7261560",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 2,
            "username": "Soker",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rodriguezviautobias@gmail.com",
            "name": "Tobias",
            "lastname": "Rodriguez Viau",
            "dni": 41216459,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "Calle Falsa 123",
            "role": "ADMIN",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 2,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "412505",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 68,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "8460643",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 3,
            "username": "duebel0",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "duebel0@dailymotion.com",
            "name": "Demott",
            "lastname": "Uebel",
            "dni": 26548921,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "099 Bunker Hill Trail",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 5,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "3456156",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 4,
            "username": "vgullivan1",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "vgullivan1@netscape.com",
            "name": "Vannie",
            "lastname": "Gullivan",
            "dni": 39965555,
            "city": {
                "idCity": 3,
                "cityName": "Necochea",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2262"
            },
            "address": "06 Luster Way",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 60,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "9413192",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 5,
            "username": "lbeat2",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "lbeat2@nyu.edu",
            "name": "Leila",
            "lastname": "Beat",
            "dni": 18055777,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "70849 Blaine Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 20,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "6191557",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 25,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "3138217",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 54,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "1573570",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 6,
            "username": "alainge3",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "alainge3@woothemes.com",
            "name": "Ameline",
            "lastname": "Lainge",
            "dni": 17733987,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "61 Magdeline Park",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 86,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "7485195",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 7,
            "username": "chuc4",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "chuc4@ftc.gov",
            "name": "Corby",
            "lastname": "Huc",
            "dni": 32364518,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "2 Towne Drive",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 88,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "7117741",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 8,
            "username": "sguidini5",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "sguidini5@sfgate.com",
            "name": "Silva",
            "lastname": "Guidini",
            "dni": 32936147,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "22175 Bay Circle",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 9,
            "username": "apeyntue6",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "apeyntue6@tinyurl.com",
            "name": "Alvin",
            "lastname": "Peyntue",
            "dni": 15799689,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "6084 Lake View Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 53,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "6150966",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 10,
            "username": "amcleman7",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "amcleman7@intel.com",
            "name": "Aluin",
            "lastname": "McLeman",
            "dni": 33178319,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "62 Memorial Court",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 11,
            "username": "zbaugh8",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "zbaugh8@bing.com",
            "name": "Zahara",
            "lastname": "Baugh",
            "dni": 19061342,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "80 Miller Park",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 37,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "7608900",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 12,
            "username": "wgarett9",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "wgarett9@flickr.com",
            "name": "Wilt",
            "lastname": "Garett",
            "dni": 31919301,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "01 Grasskamp Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 67,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "6339984",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 98,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "3788082",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 13,
            "username": "rkittoa",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rkittoa@blogtalkradio.com",
            "name": "Ros",
            "lastname": "Kitto",
            "dni": 36236666,
            "city": {
                "idCity": 3,
                "cityName": "Necochea",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2262"
            },
            "address": "5151 Mesta Trail",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 8,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "5862927",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 30,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "1377869",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 14,
            "username": "ntorrib",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ntorrib@tinypic.com",
            "name": "Nananne",
            "lastname": "Torri",
            "dni": 34293383,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "30707 Mockingbird Point",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 63,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "3255592",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 15,
            "username": "ggarrattleyc",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ggarrattleyc@nyu.edu",
            "name": "Germana",
            "lastname": "Garrattley",
            "dni": 26935740,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "810 Lakewood Gardens Way",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 45,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "5036041",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 73,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "1038533",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 16,
            "username": "rabaded",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rabaded@army.mil",
            "name": "Roland",
            "lastname": "Abade",
            "dni": 24350879,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "3 Holmberg Parkway",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 17,
            "username": "ajakubowskie",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ajakubowskie@wp.com",
            "name": "Aubrie",
            "lastname": "Jakubowski",
            "dni": 17206840,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "31756 Mccormick Place",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 69,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "6673729",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 18,
            "username": "yjolliffef",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "yjolliffef@fda.gov",
            "name": "Yves",
            "lastname": "Jolliffe",
            "dni": 29154178,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "19 Dennis Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 43,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "2099555",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 100,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "2106568",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 19,
            "username": "pwadesong",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "pwadesong@va.gov",
            "name": "Perry",
            "lastname": "Wadeson",
            "dni": 20099799,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "935 Anthes Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 41,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "9870643",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 52,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "4897767",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 20,
            "username": "eticksallh",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "eticksallh@studiopress.com",
            "name": "Elston",
            "lastname": "Ticksall",
            "dni": 23375975,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "6 Larry Street",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 21,
            "username": "brhubottomi",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "brhubottomi@timesonline.co.uk",
            "name": "Brose",
            "lastname": "Rhubottom",
            "dni": 35243813,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "4859 Mandrake Parkway",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 22,
            "username": "rvalenssmithj",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rvalenssmithj@amazon.co.jp",
            "name": "Ronnie",
            "lastname": "Valens-Smith",
            "dni": 18822039,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "0670 Emmet Street",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 23,
            "username": "naldrenk",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "naldrenk@istockphoto.com",
            "name": "Nessie",
            "lastname": "Aldren",
            "dni": 37723839,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "2 Oakridge Place",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 24,
            "username": "npierrepointl",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "npierrepointl@gnu.org",
            "name": "Nicky",
            "lastname": "Pierrepoint",
            "dni": 19803675,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "08 Eliot Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 10,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "9605421",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 77,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "1684975",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 25,
            "username": "tvigerm",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "tvigerm@amazon.co.uk",
            "name": "Thorvald",
            "lastname": "Viger",
            "dni": 43747576,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "79762 Manitowish Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 12,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "8118949",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 33,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "5226104",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 58,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "4883478",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 74,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "2317101",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 94,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "2763612",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 26,
            "username": "sbenn",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "sbenn@msn.com",
            "name": "Stu",
            "lastname": "Ben",
            "dni": 20749618,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "81225 Eastwood Hill",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 57,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "1229731",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 65,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "1597786",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 27,
            "username": "spostianso",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "spostianso@artisteer.com",
            "name": "Shelli",
            "lastname": "Postians",
            "dni": 29823498,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "6 Buell Hill",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 28,
            "username": "bheusticep",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "bheusticep@prweb.com",
            "name": "Babara",
            "lastname": "Heustice",
            "dni": 32012826,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "3 Morrow Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 29,
            "username": "aredmanq",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "aredmanq@miitbeian.gov.cn",
            "name": "Anet",
            "lastname": "Redman",
            "dni": 42851896,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "4 Knutson Trail",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 30,
            "username": "efaringtonr",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "efaringtonr@webnode.com",
            "name": "Errol",
            "lastname": "Farington",
            "dni": 43393904,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "3333 Bobwhite Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 24,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "2221318",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 36,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "9101212",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 55,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "3461076",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 31,
            "username": "xkemshells",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "xkemshells@mit.edu",
            "name": "Ximenez",
            "lastname": "Kemshell",
            "dni": 40306619,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "5 Stone Corner Court",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 14,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "4678017",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 32,
            "username": "mlentet",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "mlentet@hubpages.com",
            "name": "Marshall",
            "lastname": "Lente",
            "dni": 37231533,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "0395 Bowman Alley",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 42,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "3432216",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 33,
            "username": "ldavidowichu",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ldavidowichu@google.com.hk",
            "name": "Lefty",
            "lastname": "Davidowich",
            "dni": 42337207,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "6 Corscot Parkway",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 39,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "1732779",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 34,
            "username": "smcduffyv",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "smcduffyv@over-blog.com",
            "name": "Savina",
            "lastname": "McDuffy",
            "dni": 27649879,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "8 Starling Point",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 56,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "7505563",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 35,
            "username": "ioveralw",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ioveralw@phoca.cz",
            "name": "Isidoro",
            "lastname": "Overal",
            "dni": 35145338,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "6 5th Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 48,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "5487503",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 36,
            "username": "pvaskovx",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "pvaskovx@pbs.org",
            "name": "Phillis",
            "lastname": "Vaskov",
            "dni": 42484141,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "117 Anniversary Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 37,
            "username": "jcotsfordy",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jcotsfordy@jalbum.net",
            "name": "Janeta",
            "lastname": "Cotsford",
            "dni": 41430251,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "6 Larry Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 51,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "8081915",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 38,
            "username": "oferneleyz",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "oferneleyz@diigo.com",
            "name": "Odo",
            "lastname": "Ferneley",
            "dni": 21290067,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "9497 Bluestem Circle",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 38,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "4669203",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 76,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "1912831",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 39,
            "username": "jsalan10",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jsalan10@oracle.com",
            "name": "Josepha",
            "lastname": "Salan",
            "dni": 36798337,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "13329 Menomonie Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 11,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "3730060",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 40,
            "username": "ddagon11",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ddagon11@springer.com",
            "name": "Darren",
            "lastname": "Dagon",
            "dni": 34994083,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "9 Eastlawn Point",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 16,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "3415217",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 41,
            "username": "agetten12",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "agetten12@wsj.com",
            "name": "Aldin",
            "lastname": "Getten",
            "dni": 15324468,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "17 Miller Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 42,
            "username": "aconnors13",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "aconnors13@cisco.com",
            "name": "Ardisj",
            "lastname": "Connors",
            "dni": 25086590,
            "city": {
                "idCity": 3,
                "cityName": "Necochea",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2262"
            },
            "address": "65 Nancy Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 43,
            "username": "vkears14",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "vkears14@meetup.com",
            "name": "Veriee",
            "lastname": "Kears",
            "dni": 24859648,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "602 Del Mar Alley",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 6,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "7113169",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 44,
            "username": "vmcareavey15",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "vmcareavey15@cloudflare.com",
            "name": "Virgie",
            "lastname": "McAreavey",
            "dni": 43545251,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "36623 Bay Terrace",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 31,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "2090542",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 84,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "9917384",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 45,
            "username": "phaville16",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "phaville16@comsenz.com",
            "name": "Pierrette",
            "lastname": "Haville",
            "dni": 29797291,
            "city": {
                "idCity": 3,
                "cityName": "Necochea",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2262"
            },
            "address": "2539 Lindbergh Hill",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 66,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "2560024",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 46,
            "username": "llongina17",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "llongina17@ehow.com",
            "name": "Lyon",
            "lastname": "Longina",
            "dni": 17579817,
            "city": {
                "idCity": 3,
                "cityName": "Necochea",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2262"
            },
            "address": "23 Mayfield Road",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 64,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "6625944",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 79,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "4769340",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 47,
            "username": "thannabus18",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "thannabus18@domainmarket.com",
            "name": "Teodoro",
            "lastname": "Hannabus",
            "dni": 29068216,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "9767 Beilfuss Road",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 48,
            "username": "ofossey19",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ofossey19@taobao.com",
            "name": "Ogdan",
            "lastname": "Fossey",
            "dni": 42807307,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "4 Algoma Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 49,
            "username": "dspeare1a",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "dspeare1a@uol.com.br",
            "name": "Davey",
            "lastname": "Speare",
            "dni": 15287562,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "72071 School Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 3,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "4155176",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 59,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "6339009",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 50,
            "username": "mwiddecombe1b",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "mwiddecombe1b@skyrock.com",
            "name": "Meris",
            "lastname": "Widdecombe",
            "dni": 20373986,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "08818 Prentice Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 4,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "9464855",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 51,
            "username": "cbanister1c",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "cbanister1c@engadget.com",
            "name": "Constantine",
            "lastname": "Banister",
            "dni": 31221463,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "2779 Sugar Crossing",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 52,
            "username": "pstolle1d",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "pstolle1d@acquirethisname.com",
            "name": "Pearline",
            "lastname": "Stolle",
            "dni": 33314817,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "7744 Acker Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 19,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "5311449",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 53,
            "username": "wpearde1e",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "wpearde1e@jimdo.com",
            "name": "Wynny",
            "lastname": "Pearde",
            "dni": 37066451,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "04065 Vernon Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 54,
            "username": "mcamelli1f",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "mcamelli1f@ebay.co.uk",
            "name": "Misti",
            "lastname": "Camelli",
            "dni": 23328393,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "65 Meadow Vale Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 55,
            "username": "ttalton1g",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ttalton1g@quantcast.com",
            "name": "Tess",
            "lastname": "Talton",
            "dni": 27468933,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "91771 Vahlen Plaza",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 56,
            "username": "abispham1h",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "abispham1h@time.com",
            "name": "Agneta",
            "lastname": "Bispham",
            "dni": 28386264,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "007 Sunbrook Road",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 44,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "1343833",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 82,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "9376108",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 57,
            "username": "lmathivet1i",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "lmathivet1i@ucla.edu",
            "name": "Lemar",
            "lastname": "Mathivet",
            "dni": 24067960,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "684 East Park",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 58,
            "username": "htroughton1j",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "htroughton1j@spotify.com",
            "name": "Herrick",
            "lastname": "Troughton",
            "dni": 39142270,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "60 Nancy Court",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 59,
            "username": "jlynd1k",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jlynd1k@purevolume.com",
            "name": "Jemie",
            "lastname": "Lynd",
            "dni": 34964608,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "0 Pennsylvania Court",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 60,
            "username": "cmurrthum1l",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "cmurrthum1l@plala.or.jp",
            "name": "Christophorus",
            "lastname": "Murrthum",
            "dni": 17325405,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "1 Karstens Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 92,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "5099304",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 93,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "9526473",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 95,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "7674983",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 61,
            "username": "cgeldeard1m",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "cgeldeard1m@hao123.com",
            "name": "Cynthea",
            "lastname": "Geldeard",
            "dni": 32816597,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "56365 Myrtle Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 62,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "4111354",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 62,
            "username": "jscoullar1n",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jscoullar1n@mlb.com",
            "name": "Joyann",
            "lastname": "Scoullar",
            "dni": 32869430,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "19 Haas Terrace",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 63,
            "username": "tmcilwraith1o",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "tmcilwraith1o@live.com",
            "name": "Toby",
            "lastname": "McIlwraith",
            "dni": 23362978,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "62 High Crossing Alley",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 9,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "1471419",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 64,
            "username": "awicher1p",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "awicher1p@latimes.com",
            "name": "Adriena",
            "lastname": "Wicher",
            "dni": 29004332,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "21 Huxley Parkway",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 23,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "6975705",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 72,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "4497365",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 65,
            "username": "dcushelly1q",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "dcushelly1q@51.la",
            "name": "Deeanne",
            "lastname": "Cushelly",
            "dni": 23893879,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "249 Victoria Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 66,
            "username": "mgillespey1r",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "mgillespey1r@irs.gov",
            "name": "Murdock",
            "lastname": "Gillespey",
            "dni": 30626404,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "25386 Park Meadow Crossing",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 34,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "1194363",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 67,
            "username": "jcaines1s",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jcaines1s@dedecms.com",
            "name": "Joela",
            "lastname": "Caines",
            "dni": 23859189,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "48282 Hallows Street",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 28,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "3692339",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 81,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "2796632",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 68,
            "username": "jproske1t",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jproske1t@ycombinator.com",
            "name": "Josephine",
            "lastname": "Proske",
            "dni": 33333356,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "99 Bunker Hill Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 49,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "1513662",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 69,
            "username": "flattimore1u",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "flattimore1u@samsung.com",
            "name": "Freddy",
            "lastname": "Lattimore",
            "dni": 25637367,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "66453 Florence Drive",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 22,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "2567757",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 35,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "6759301",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 50,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "5982281",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 75,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "4555869",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 70,
            "username": "wmoorwood1v",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "wmoorwood1v@acquirethisname.com",
            "name": "Werner",
            "lastname": "Moorwood",
            "dni": 26565007,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "51921 Vahlen Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 61,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "8532006",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 80,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "6309753",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 71,
            "username": "agathercole1w",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "agathercole1w@sphinn.com",
            "name": "Artemus",
            "lastname": "Gathercole",
            "dni": 26138229,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "0 Truax Drive",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 32,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "4531090",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 90,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "4236322",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 72,
            "username": "scrackett1x",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "scrackett1x@cocolog-nifty.com",
            "name": "Sarita",
            "lastname": "Crackett",
            "dni": 31413381,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "8621 Brickson Park Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 85,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "8995537",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 73,
            "username": "rcaslin1y",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rcaslin1y@ask.com",
            "name": "Rheba",
            "lastname": "Caslin",
            "dni": 24566851,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "947 Annamark Park",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 83,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "1860250",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 99,
                    "city": {
                        "idCity": 2,
                        "cityName": "Miramar",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2291"
                    },
                    "phoneNumber": "2638911",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 74,
            "username": "aheinl1z",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "aheinl1z@meetup.com",
            "name": "Averil",
            "lastname": "Heinl",
            "dni": 15626945,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "42 Namekagon Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 96,
                    "city": {
                        "idCity": 1,
                        "cityName": "Mar del Plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "223"
                    },
                    "phoneNumber": "9868979",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 75,
            "username": "whendonson20",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "whendonson20@mozilla.org",
            "name": "Willabella",
            "lastname": "Hendonson",
            "dni": 27999624,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "08 Eagle Crest Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 27,
                    "city": {
                        "idCity": 8,
                        "cityName": "La plata",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "221"
                    },
                    "phoneNumber": "6232279",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 76,
            "username": "jantrim21",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jantrim21@rakuten.co.jp",
            "name": "Jefferey",
            "lastname": "Antrim",
            "dni": 25556822,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "956 Spenser Point",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 47,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "9954776",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 77,
            "username": "trivelin22",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "trivelin22@howstuffworks.com",
            "name": "Tresa",
            "lastname": "Rivelin",
            "dni": 22816778,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "940 Schmedeman Trail",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 70,
                    "city": {
                        "idCity": 5,
                        "cityName": "Balcarce",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2266"
                    },
                    "phoneNumber": "1629195",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 78,
            "username": "deburah23",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "deburah23@google.nl",
            "name": "Derril",
            "lastname": "Eburah",
            "dni": 40086503,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "572 Sheridan Terrace",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 79,
            "username": "fluberto24",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "fluberto24@meetup.com",
            "name": "Francois",
            "lastname": "Luberto",
            "dni": 28784248,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "739 Acker Street",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 18,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "3655135",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 80,
            "username": "wlansberry25",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "wlansberry25@list-manage.com",
            "name": "Waylan",
            "lastname": "Lansberry",
            "dni": 40425125,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "94445 Lakeland Road",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 40,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "2046194",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 81,
            "username": "nwhitsun26",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "nwhitsun26@aboutads.info",
            "name": "Nollie",
            "lastname": "Whitsun",
            "dni": 23053784,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "8 Hintze Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 71,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "5905747",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 82,
            "username": "jhandslip27",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jhandslip27@odnoklassniki.ru",
            "name": "Jaquelyn",
            "lastname": "Handslip",
            "dni": 15859686,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "4138 Trailsway Street",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 83,
            "username": "dmoulding28",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "dmoulding28@pagesperso-orange.fr",
            "name": "Dosi",
            "lastname": "Moulding",
            "dni": 30077935,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "1 Blaine Point",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 78,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "2401886",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 84,
            "username": "ckniveton29",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ckniveton29@meetup.com",
            "name": "Clare",
            "lastname": "Kniveton",
            "dni": 41693126,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "2812 Bashford Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 89,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "3429158",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 85,
            "username": "ggreenstead2a",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ggreenstead2a@163.com",
            "name": "Granny",
            "lastname": "Greenstead",
            "dni": 18147221,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "5823 Chinook Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 86,
            "username": "mdyers2b",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "mdyers2b@guardian.co.uk",
            "name": "Mayer",
            "lastname": "Dyers",
            "dni": 29099316,
            "city": {
                "idCity": 7,
                "cityName": "Ciudad Autonoma de Buenos Aires",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "011"
            },
            "address": "01807 Elgar Drive",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 17,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "9788484",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 46,
                    "city": {
                        "idCity": 4,
                        "cityName": "Bahia Blanca",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "291"
                    },
                    "phoneNumber": "4185732",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 87,
            "username": "cbarchrameev2c",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "cbarchrameev2c@aboutads.info",
            "name": "Cesaro",
            "lastname": "Barchrameev",
            "dni": 34807799,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "12 Rigney Alley",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 29,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "6997606",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 88,
            "username": "csalzen2d",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "csalzen2d@seesaa.net",
            "name": "Constantino",
            "lastname": "Salzen",
            "dni": 24996721,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "484 Kensington Circle",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 7,
                    "city": {
                        "idCity": 6,
                        "cityName": "Olavarria",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2284"
                    },
                    "phoneNumber": "4855135",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 13,
                    "city": {
                        "idCity": 9,
                        "cityName": "Cordoba Capital",
                        "province": {
                            "idProvince": 3,
                            "provinceName": "Cordoba"
                        },
                        "prefix": "351"
                    },
                    "phoneNumber": "9676506",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 89,
            "username": "nwitherow2e",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "nwitherow2e@angelfire.com",
            "name": "Nicol",
            "lastname": "Witherow",
            "dni": 21344655,
            "city": {
                "idCity": 6,
                "cityName": "Olavarria",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2284"
            },
            "address": "9789 Lunder Hill",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 97,
                    "city": {
                        "idCity": 3,
                        "cityName": "Necochea",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "2262"
                    },
                    "phoneNumber": "6453135",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 90,
            "username": "ascrogges2f",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ascrogges2f@yellowpages.com",
            "name": "Artair",
            "lastname": "Scrogges",
            "dni": 41751491,
            "city": {
                "idCity": 9,
                "cityName": "Cordoba Capital",
                "province": {
                    "idProvince": 3,
                    "provinceName": "Cordoba"
                },
                "prefix": "351"
            },
            "address": "54 Morning Road",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 91,
            "username": "ksperling2g",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "ksperling2g@angelfire.com",
            "name": "Katya",
            "lastname": "Sperling",
            "dni": 42199093,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "65000 Quincy Trail",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 92,
            "username": "bsyce2h",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "bsyce2h@a8.net",
            "name": "Benjamin",
            "lastname": "Syce",
            "dni": 17611888,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "330 Sunnyside Avenue",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 21,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "5628958",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 93,
            "username": "jhyams2i",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "jhyams2i@symantec.com",
            "name": "Jeniffer",
            "lastname": "Hyams",
            "dni": 43331088,
            "city": {
                "idCity": 5,
                "cityName": "Balcarce",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2266"
            },
            "address": "4605 Garrison Street",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 15,
                    "city": {
                        "idCity": 10,
                        "cityName": "Santa Rosa",
                        "province": {
                            "idProvince": 2,
                            "provinceName": "La Pampa"
                        },
                        "prefix": "2954"
                    },
                    "phoneNumber": "3027481",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 94,
            "username": "hsmeal2j",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "hsmeal2j@amazon.co.uk",
            "name": "Hodge",
            "lastname": "Smeal",
            "dni": 20633190,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "6 Gale Trail",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 95,
            "username": "uciobotaru2k",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "uciobotaru2k@bluehost.com",
            "name": "Umberto",
            "lastname": "Ciobotaru",
            "dni": 38288305,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "00417 Mallard Center",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 96,
            "username": "rslateford2l",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rslateford2l@google.ru",
            "name": "Robbin",
            "lastname": "Slateford",
            "dni": 15538079,
            "city": {
                "idCity": 8,
                "cityName": "La plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "221"
            },
            "address": "3235 Melrose Plaza",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": [
                {
                    "idLine": 26,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "7603644",
                    "lineType": "RESIDENTIAL",
                    "lineStatus": "ACTIVE"
                },
                {
                    "idLine": 91,
                    "city": {
                        "idCity": 7,
                        "cityName": "Ciudad Autonoma de Buenos Aires",
                        "province": {
                            "idProvince": 1,
                            "provinceName": "Buenos Aires"
                        },
                        "prefix": "011"
                    },
                    "phoneNumber": "5982727",
                    "lineType": "MOBILE",
                    "lineStatus": "ACTIVE"
                }
            ]
        },
        {
            "idUser": 97,
            "username": "vspitell2m",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "vspitell2m@unicef.org",
            "name": "Vivi",
            "lastname": "Spitell",
            "dni": 27035504,
            "city": {
                "idCity": 3,
                "cityName": "Necochea",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2262"
            },
            "address": "1 Manitowish Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 98,
            "username": "apaulon2n",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "apaulon2n@oakley.com",
            "name": "Anatola",
            "lastname": "Paulon",
            "dni": 31357980,
            "city": {
                "idCity": 10,
                "cityName": "Santa Rosa",
                "province": {
                    "idProvince": 2,
                    "provinceName": "La Pampa"
                },
                "prefix": "2954"
            },
            "address": "4711 Harper Way",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 99,
            "username": "rofairy2o",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "rofairy2o@pagesperso-orange.fr",
            "name": "Rouvin",
            "lastname": "O'Fairy",
            "dni": 19801647,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "00650 John Wall Pass",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 100,
            "username": "lflieg2p",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "lflieg2p@admin.ch",
            "name": "Lutero",
            "lastname": "Flieg",
            "dni": 43890757,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "97972 Sherman Crossing",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 101,
            "username": "wfenton2q",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "wfenton2q@dell.com",
            "name": "Wells",
            "lastname": "Fenton",
            "dni": 39953715,
            "city": {
                "idCity": 4,
                "cityName": "Bahia Blanca",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "291"
            },
            "address": "74108 Dwight Junction",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 102,
            "username": "fbeamson2r",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "fbeamson2r@about.com",
            "name": "Ferrel",
            "lastname": "Beamson",
            "dni": 40488116,
            "city": {
                "idCity": 2,
                "cityName": "Miramar",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "2291"
            },
            "address": "33 Quincy Lane",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 103,
            "username": "nuevoser",
            "password": "$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.",
            "email": "falsolian@hotmail.com.ar",
            "name": "Christian",
            "lastname": "Soldano",
            "dni": 444444,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "Manuel Acevedo 2685",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        },
        {
            "idUser": 104,
            "username": "noser",
            "password": "$2a$10$YYvi1MiszH13DTNkryHgu.5xmobCZ03Ci//mhCE7OnAxUobYWmJPe",
            "email": "falsaan@hotmail.com.ar",
            "name": "Christian",
            "lastname": "Soldano",
            "dni": 444344,
            "city": {
                "idCity": 1,
                "cityName": "Mar del Plata",
                "province": {
                    "idProvince": 1,
                    "provinceName": "Buenos Aires"
                },
                "prefix": "223"
            },
            "address": "Manuel Acevedo 2685",
            "role": "CLIENT",
            "status": "ACTIVE",
            "lines": []
        }
    ]);
