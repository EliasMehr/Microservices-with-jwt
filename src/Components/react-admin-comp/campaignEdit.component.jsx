import {
    Edit,
} from 'react-admin';
import EditForm from './editForm'

export const CampaignEdit = props => {
    
    const transform = data => {

        if(!data.image || typeof data.image === "string") {
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
     })

    return (
        <Edit {...props} transform={transform}>
            <EditForm/>
        </Edit>
    )
    
}