package com.ayrton.socialmedia.ui;

public class PublicationDialogFactory {

    public static PublicationDialog create(String type) {

        if (type == null) return null;

        switch (type) {
            case "FacebookText": return new FacebookTextDialog();
            case "ThreadsText": return new ThreadsTextDialog();

            case "FacebookImage": return new FacebookImageDialog();
            case "InstagramImage": return new InstagramImageDialog();
            case "TikTokImage": return new TikTokImageDialog();
            case "ThreadsImage": return new ThreadsImageDialog();

            case "FacebookVideo": return new FacebookVideoDialog();
            case "InstagramVideo": return new InstagramVideoDialog();
            case "TikTokVideo": return new TikTokVideoDialog();
            case "ThreadsVideo": return new ThreadsVideoDialog();

            case "YouTubeLongVideo": return new YouTubeLongVideoDialog();
        }

        return null;
    }
}