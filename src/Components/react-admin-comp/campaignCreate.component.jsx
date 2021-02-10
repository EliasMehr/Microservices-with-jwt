import {
    Create
} from 'react-admin';

import React from 'react';
import CreateForm from './createForm.component';


export const CampaignCreate = props => {


    const transform = data => {

        if(!data.image) {
            return ({
                ...data
            })
        }
        const promiseImgArray = convertFileToArray(data.image);        
        return promiseImgArray.then(convertedImg =>  ({
            ...data,
            image: [...convertedImg]
        }))
        
            
    }

    const convertFileToArray = file =>
        new Promise((resolve, reject) => {

            console.log(file);
            const reader = new FileReader();
            reader.onload = () => {
            const bytes = new Uint8Array(reader.result);
            bytes ? resolve(bytes) : reject('error');
            }
            

            reader.onerror = reject;
            reader.readAsArrayBuffer(file.rawFile);
            //  return reject("HEJJJJJJJJJJJJJJ");
     })




    return (
         <Create {...props} transform={transform}>
            <CreateForm redirect="list"/>
        </Create>
    )
}

