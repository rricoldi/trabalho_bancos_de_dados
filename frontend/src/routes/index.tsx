import React, { useState } from 'react'
import { Switch, Route } from 'react-router-dom'

import Feed from '../pages/Feed'
import PodcastRegister from '../pages/PodcastRegister'
import UserRegister from '../pages/UserRegister'
import Podcast from '../pages/Podcast'
import AuthContext, { AuthContextData } from '../context/AuthContext'

const Routes: React.FC = () => {
    const [context, setContext] = useState<AuthContextData>({
        logged: true,
        id: '4fa6b567-6871-4287-9e31-c702045fd549',
        name: 'Renan Ricoldi',
        sex: 'male',
        podcastUrl: '',
        setData: (data: AuthContextData) => { setContext(data) }
    })

    return (
        <AuthContext.Provider value={{... context}}>
            <Switch>
                <Route path='/' exact component={Feed} />
                <Route path='/podcast/register' exact component={PodcastRegister} />
                <Route path='/user/register' exact component={UserRegister} />
                <Route path='/podcast/:id' exact component={Podcast} />
            </Switch>
        </AuthContext.Provider>

    )
}

export default Routes
