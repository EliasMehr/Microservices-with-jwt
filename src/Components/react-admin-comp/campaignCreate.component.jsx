import {
    Create
} from 'react-admin';

import React from 'react';
import CreateForm from './createForm.component';

export const CampaignCreate = props => {
    return (
         <Create {...props}>
            <CreateForm redirect={'/company'}/>
        </Create>
    )
}

