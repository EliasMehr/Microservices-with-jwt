import {useState, useEffect} from 'react';
import {
    FormWithRedirect,
    TextInput,
    Toolbar,
    SaveButton,
    DeleteButton,
    DateTimeInput,
    SelectInput,
} from 'react-admin';
import { Typography, Box, Button} from '@material-ui/core';
import CreateCampaignCard from './createCampaignCard.component'

const CreateForm = (props) => {

    
    const [campaign, setCampaign] = useState({
        title: '',
        description: '',
        category: '',
        discount: '',
        publishedAt: '',
        expiresAt: '',
        isPercentage: '',
        image: '',
        discountCode: ''
      });

      useEffect(() => {
          console.log("new state", campaign)
      }, [campaign])

    
      const handleChange = (prop) => (e) => {
        setCampaign({ ...campaign, [prop]: e.target.value})
           
      }


    return (
        <FormWithRedirect
        {...props}
        render={formProps => (
            // here starts the custom form layout
            <form>
                <Box p="1em" display="flex" justifyContent='center'>
                    <Box display="flex">
                        <Box flex={2} mr="1em">

                            <Typography variant="h6" gutterBottom>Campaign</Typography>

                            <Box display="flex">
                                <Box flex={1} mr="0.5em">
                                    <TextInput 
                                    source="title" 
                                    fullWidth 
                                    required
                                    value={campaign.title}
                                    onChange={handleChange('title')}
                                    />
                                </Box>
                                <Box flex={1} ml="0.5em">
                                    <TextInput 
                                    source="category" 
                                    fullWidth 
                                    required
                                    value={campaign.category}
                                    onChange={handleChange('category')}
                                    />
                                </Box>
                            </Box>

                            <TextInput 
                            source="description" 
                            fullWidth 
                            multiline
                            value={campaign.description}
                            onChange={handleChange('description')}
                            />
                            <TextInput 
                            source="image" 
                            fullWidth
                            value={campaign.image}
                            onChange={handleChange('image')}
                            />


                            <Typography variant="h6" gutterBottom>Discount</Typography>
                            <Box display="flex">
                                <Box flex={1} mr="0.5em">
                                    <TextInput 
                                    source="discount" 
                                    type="number" 
                                    fullWidth
                                    required
                                    value={campaign.discount}
                                    onChange={handleChange('discount')}
                                    />
                                </Box>
                                <Box >
                                    <SelectInput source="isPercentage" label="Discount Type" onChange={handleChange('isPercentage')} choices={[
                                        { id: true, name: 'Percentage' },
                                        { id: false, name: 'SEK'}
                                    ]} />

                                </Box>
                                <Box flex={1} ml="0.5em">
                                    <TextInput 
                                    source="discountCode" 
                                    fullWidth
                                    required
                                    value={campaign.discountCode}
                                    onChange={handleChange('discountCode')}
                                    />
                                </Box>
                            </Box>
                            <Box mt="1em" />

                            <Typography variant="h6" gutterBottom>Publish Info, Leave empty if you wanna publish now</Typography>
                            <Box display="flex">
                                <Box flex={1} mr="0.5em">
                                    <DateTimeInput 
                                    source="publishAt"  
                                    fullWidth 
                                    value={campaign.publishedAt}
                                    onChange={handleChange('publishedAt')}
                                    />
                                </Box>
                                <Box flex={2} ml="0.5em">
                                    <DateTimeInput 
                                    source="expiresAt" 
                                    fullWidth 
                                    value={campaign.expiresAt}
                                    onChange={handleChange('expiresAt')}
                                    />
                                </Box>
                            </Box>
                        </Box>

                        <Box flex={1} ml="1em">
                            <CreateCampaignCard {...campaign}/>
                        </Box>

                    </Box>
                </Box>
                <Toolbar>
                    <Box display="flex" justifyContent="center" width="100%">
                   
                        <Button variant='primary'>Go Back</Button>               
                        <SaveButton
                            saving={formProps.saving}
                            handleSubmitWithRedirect={formProps.handleSubmitWithRedirect}
                        />
                        <DeleteButton record={formProps.record} />
                    </Box>
                </Toolbar>
            </form>
        )}
    />
    )
}

export default CreateForm;