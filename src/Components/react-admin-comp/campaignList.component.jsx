import { List, Datagrid, TextField, DateField, BooleanField, EditButton } from 'react-admin';

export const CampaignList = (props) => (
    <List {...props}>
        <Datagrid>
            <TextField source="title" />
            <TextField source="description" />
            <TextField source="discount" />
            <TextField source="category" />
            <TextField source="discountCode" />
            <DateField source="createdAt" />
            <DateField source="publishedAt" />
            <DateField source="updatedAt" />
            <DateField source="expiresAt" />
            <BooleanField source="isPercentage" />
            <BooleanField source="isPublished" />
            <EditButton />
        </Datagrid>
    </List>
);