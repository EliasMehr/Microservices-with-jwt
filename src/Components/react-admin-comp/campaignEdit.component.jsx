import {
    Edit,
    SimpleForm,
    DateTimeInput,
    NumberInput,
    BooleanInput,
    TextInput,
} from 'react-admin';

export const CampaignEdit = props => (
    <Edit {...props}>
        <SimpleForm>
            <TextInput source="title" />
            <TextInput source="description" />
            <NumberInput source="discount" />
            <TextInput source="image" />
            <TextInput source="category" />
            <DateTimeInput source="publishedAt" showTime/>
            <DateTimeInput source="expiresAt" />
            <TextInput source="discountCode" />
            <BooleanInput source="isPercentage" />
            <BooleanInput source="isPublished" />
        </SimpleForm>
    </Edit>
);