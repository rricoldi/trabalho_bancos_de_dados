import React from 'react'
import { Switch, Route } from 'react-router-dom'

import Feed from '../pages/Feed'
import PodcastRegister from '../pages/PodcastRegister'
import UserRegister from '../pages/UserRegister'

const Routes: React.FC = () => (
    <Switch>
        <Route path='/' exact component={Feed} />
        <Route path='/podcast/register' exact component={PodcastRegister} />
        <Route path='/user/register' exact component={UserRegister} />
    </Switch>
)

export default Routes
