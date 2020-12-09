import React from 'react'

import CardContainer from '../../Components/CardContainer'
import Header from '../../Components/Header'

const Feed: React.FC = () => {
    return (
        <>
            <Header logado={true} nome='Renan' sexo='male'/>
            <CardContainer />
        </>
    )
}

export default Feed
