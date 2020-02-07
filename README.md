# Flix
Flix is an app that allows users to browse movies from the [The Movie Database API](http://docs.themoviedb.apiary.io/#).

## User Stories

- User can view a list of movies (title, poster image, and overview) currently playing in theaters from the Movie Database API.
- Views are responsive for both landscape/portrait mode.
- In portrait mode, the poster image, title, and movie overview is shown.
- In landscape mode, the rotated alternate layout uses the backdrop image instead and shows the title and movie overview to the right of it.
- Displays a default placeholder graphic for each image during loading

- Improved the user interface by experimenting with styling and coloring.
- For popular movies (a movie voted for more than 5 stars), the full backdrop image is displayed. Otherwise, a poster image, the movie title, and overview is listed. Uses Heterogenous RecyclerViews and different ViewHolder layout files for popular movies and less popular ones.
- Exposes details of movie (ratings using RatingBar, popularity, and synopsis) in a separate activity.
- Allows video posts to be played in full-screen using the YouTubePlayerView
   - When clicking on a popular movie (i.e. a movie voted for more than 5 stars) the video is played immediately.
   - Less popular videos rely on the detailed page to show an image preview that can initiate playing a YouTube video.
   
- Added a play icon overlay to popular movies to indicate that the movie can be played.
- Applied the popular ButterKnife annotation library to reduce view boilerplate.
- Added a rounded corners for the images using the Glide transformations.

### App Walkthough GIF

<img src="https://media.giphy.com/media/1cw1A1ksvSp8khzeoi/giphy.gif" width=250><br>

### Notes
The rounded corners transformation created unexpected padding around the poster images. Also, a ScrollView was necessary to view all information in the landscape-mode detail screen.

The Glide library's .placeholder was a slight problem, it didn't work properly as stated in the resources. Also, the landscape mode .gif won't work in this README even though the portrait mode does.

## Open-source libraries used
- [Android Async HTTP](https://github.com/loopj/android-async-http) - Simple asynchronous HTTP requests with JSON parsing
- [Glide](https://github.com/bumptech/glide) - Image loading and caching library for Android
