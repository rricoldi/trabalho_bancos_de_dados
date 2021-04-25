import React, { useState } from 'react'
import { Switch, Route } from 'react-router-dom'

import Feed from '../pages/Feed'
import PodcastRegister from '../pages/PodcastRegister'
import UserRegister from '../pages/UserRegister'
import UserLogin from '../pages/UserLogin'
import Podcast from '../pages/Podcast'
import AuthContext, { AuthContextData } from '../context/AuthContext'
import Log from '../pages/Log'

const Routes: React.FC = () => {
    const [context, setContext] = useState<AuthContextData>({
        logged: false,
        id: '',
        name: '',
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
                <Route path='/user/login' exact component={UserLogin} />
                <Route path='/podcast/:id' exact component={Podcast} />
                <Route path='/log' exact component={Log} />
            </Switch>
        </AuthContext.Provider>

    )
}

export default Routes
