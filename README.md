# deish


This app was created on the weekend of [Vanhack](vanhack.com)'s and Skip The Dishes [Recruiting Fair](http://www.vanhack.com/saopaulo/).

The challenge was: 

Build a solution to use an API to:
- Authenticate; [almost done, it's authenticating with an existing account but still needs to create a new one]
- Add selected products to a cart; [done]
- Place one or more Orders; [you can place your order and go back to place more]
- Cancel an Order; [not required in the event]
- View the Order’s Status; [you can view all your orders with the value, contact info and status]

To login follow the instructions in the app or below:

- name and address are optional (wip) for registration use only
- username: user@user.com
- password: passwd


App's flow: 

- Login (duh)
- **Main Activity:** 
    - The list of stores is displayed, select one to see the products 
    - or the floating action button to display previous orders
- **Store Detail Activity:** 
    - Tap on a product to add to the cart (wip, dialog to confirm or cancel that you want to add, displaying the description of that product
    - Press the button to view the cart with values
- **Cart Activity:**
    - View the order and submit
    - Activity finishes, returning to the store

That's it for now :) 
