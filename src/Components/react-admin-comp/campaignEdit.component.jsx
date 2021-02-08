import {
    Edit,
} from 'react-admin';
import EditForm from './editForm'

export const CampaignEdit = props => (
    <>    

    <Edit {...props}>
        <EditForm/>
    </Edit>
    </>
);