// import jsonServerProvider from 'ra-data-simple-rest';
// import { fetchUtils, HttpError  } from 'react-admin';
// import headerRequest from '../../utils/headerRequest';





// const httpClient = (url, options = {}) => {
//     options.headers = new Headers({ Accept: 'application/json' });
//     const token = headerRequest().Authorization;
//     console.log("HEADER: " + token)
//     options.headers.set('Authorization', token);
//     return fetchUtils.fetchJson(url, options);
// }

// const dataProvider = jsonServerProvider('http://localhost:8080', httpClient, 'X-Total-Count');

// const myDataProvider = {
//     ...dataProvider,
//     create: (resource, params) => {
//         if (resource !== 'campaign' || !params.data.image) {     
//             console.log("alltid")
//             return dataProvider.create(resource, params).catch(err => console.log(err));
//         }

//         const newPictures = params.data.image;

//         console.log(newPictures);

//         const convertedImgPromise = convertFileToArray(newPictures);

//         convertedImgPromise.then(res => console.log(res));

//         return convertedImgPromise.then(pic => {
//         dataProvider.create(resource,
//                 {
//                      ...params,
//                     data: {
//                        ...params.data,
//                         image: [...pic]
//                     }
//                 })}
//           )
//     },

//     update: (resource, params) => {
//         if (resource !== 'campaign' || !params.data.image) {
//             // fallback to the default implementation
//             console.log(params);
     
//             return dataProvider.update(resource, params);
//         }

//         console.log(params);

//         const newPictures = params.data.image;

//         const convertedPic = convertFileToArray(newPictures);
//         return convertedPic.then(pic => {
//              dataProvider.update(resource,
//                 {  
//                     ...params,
//                     data: {
//                        ...params.data,
//                         image: [...pic]
//                     }
//                 })}
//           )
//     }
    
    

   
// };

// /**
//  * Convert a `File` object returned by the upload input into a base 64 string.
//  * That's not the most optimized way to store images in production, but it's
//  * enough to illustrate the idea of data provider decoration.
//  */
// //  const convertFileToBase64 = file =>
// //      new Promise((resolve, reject) => {
// //          const reader = new FileReader();
// //          reader.onload = () => resolve(reader.result);
// //          reader.onerror = reject;

// //          reader.readAsDataURL(file.rawFile);
// //      });
//  const convertFileToArray = file =>
//     new Promise((resolve, reject) => {
//         console.log(file);
//          const reader = new FileReader();
//          reader.onload = () => resolve(new Uint8Array(reader.result))
//         //  reader.onload = () => resolve(console.log(reader.result))

//          reader.onerror = reject;
//          reader.readAsArrayBuffer(file.rawFile);
//         //  return reject("HEJJJJJJJJJJJJJJ");
//      })

// export default myDataProvider;