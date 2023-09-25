import React, { Fragment, useContext } from 'react'
import Navbar from './Navbar'
import NotificationContext from '../../store/notification-context'
import Notification from './Notification';

function Layout(props) {
  const notificationContext = useContext(NotificationContext);
  const activeNotification = notificationContext.notification

  return (
    <Fragment>
      <Navbar />
      <main>{props.children}</main>
      {
        activeNotification && (
          <Notification 
            message={activeNotification.message}
            status={activeNotification.status}
          />
        )
      }
    </Fragment>
  )
}

export default Layout