package com.example.android.douban.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Derrick on 2018/6/19.
 */

public class MovieReponse {

    /**
     * count : 1
     * start : 0
     * total : 250
     * subjects : [{"rating":{"max":10,"average":9.6,"stars":"50","min":0},"genres":["犯罪","剧情"],"title":"肖申克的救赎","casts":[{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}],"collect_count":1303676,"original_title":"The Shawshank Redemption","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}],"year":"1994","images":{"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg"},"alt":"https://movie.douban.com/subject/1292052/","id":"1292052"}]
     * title : 豆瓣电影Top250
     */

    private int count;
    private int start;
    private int total;
    private String title;
    private List<Subjects> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    public static class Subjects implements Parcelable{
        /**
         * rating : {"max":10,"average":9.6,"stars":"50","min":0}
         * genres : ["犯罪","剧情"]
         * title : 肖申克的救赎
         * casts : [{"alt":"https://movie.douban.com/celebrity/1054521/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg"},"name":"蒂姆·罗宾斯","id":"1054521"},{"alt":"https://movie.douban.com/celebrity/1054534/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p34642.jpg"},"name":"摩根·弗里曼","id":"1054534"},{"alt":"https://movie.douban.com/celebrity/1041179/","avatars":{"small":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","large":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg","medium":"https://img1.doubanio.com/view/celebrity/s_ratio_celebrity/public/p5837.jpg"},"name":"鲍勃·冈顿","id":"1041179"}]
         * collect_count : 1303676
         * original_title : The Shawshank Redemption
         * subtype : movie
         * directors : [{"alt":"https://movie.douban.com/celebrity/1047973/","avatars":{"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg"},"name":"弗兰克·德拉邦特","id":"1047973"}]
         * year : 1994
         * images : {"small":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","large":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg","medium":"https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg"}
         * alt : https://movie.douban.com/subject/1292052/
         * id : 1292052
         */

        private Rating rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        private Images images;
        private String alt;
        private String id;
        private List<String> genres;
        private List<Casts> casts;
        private List<Directors> directors;
        private String desc;

        public Subjects(){

        }

        protected Subjects(Parcel in) {
            rating = in.readParcelable(Rating.class.getClassLoader());
            title = in.readString();
            collect_count = in.readInt();
            original_title = in.readString();
            subtype = in.readString();
            year = in.readString();
            images = in.readParcelable(Images.class.getClassLoader());
            alt = in.readString();
            id = in.readString();
            genres = in.createStringArrayList();
            casts = in.createTypedArrayList(Casts.CREATOR);
            directors = in.createTypedArrayList(Directors.CREATOR);
            desc = in.readString();
        }

        public static final Creator<Subjects> CREATOR = new Creator<Subjects>() {
            @Override
            public Subjects createFromParcel(Parcel in) {
                return new Subjects(in);
            }

            @Override
            public Subjects[] newArray(int size) {
                return new Subjects[size];
            }
        };

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public Images getImages() {
            return images;
        }

        public void setImages(Images images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }
        public String genresToString(){
            if(genres == null)
                return "";
            String str = "";
            str += year + " / ";
            for (String s : genres){
                str += s + " ";
            }
            str += " / ";
            for (Casts c : casts){
                str += c.getName()+" ";
            }

            return str;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<Casts> getCasts() {
            return casts;
        }

        public void setCasts(List<Casts> casts) {
            this.casts = casts;
        }

        public List<Directors> getDirectors() {
            return directors;
        }

        public void setDirectors(List<Directors> directors) {
            this.directors = directors;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(rating, flags);
            dest.writeString(title);
            dest.writeInt(collect_count);
            dest.writeString(original_title);
            dest.writeString(subtype);
            dest.writeString(year);
            dest.writeParcelable(images, flags);
            dest.writeString(alt);
            dest.writeString(id);
            dest.writeStringList(genres);
            dest.writeTypedList(casts);
            dest.writeTypedList(directors);
            dest.writeString(desc);
        }


        public static class Rating implements Parcelable{
            /**
             * max : 10
             * average : 9.6
             * stars : 50
             * min : 0
             */

            private int max;
            private double average;
            private String stars;
            private int min;

            public Rating(){

            }

            protected Rating(Parcel in) {
                max = in.readInt();
                average = in.readDouble();
                stars = in.readString();
                min = in.readInt();
            }

            public static final Creator<Rating> CREATOR = new Creator<Rating>() {
                @Override
                public Rating createFromParcel(Parcel in) {
                    return new Rating(in);
                }

                @Override
                public Rating[] newArray(int size) {
                    return new Rating[size];
                }
            };

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(max);
                dest.writeDouble(average);
                dest.writeString(stars);
                dest.writeInt(min);
            }
        }

        public static class Images implements Parcelable{
            /**
             * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg
             * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg
             * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg
             */

            private String small;
            private String large;
            private String medium;

            public Images(){

            }

            protected Images(Parcel in) {
                small = in.readString();
                large = in.readString();
                medium = in.readString();
            }

            public static final Creator<Images> CREATOR = new Creator<Images>() {
                @Override
                public Images createFromParcel(Parcel in) {
                    return new Images(in);
                }

                @Override
                public Images[] newArray(int size) {
                    return new Images[size];
                }
            };

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(small);
                dest.writeString(large);
                dest.writeString(medium);
            }
        }

        public static class Casts implements Parcelable{
            /**
             * alt : https://movie.douban.com/celebrity/1054521/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg"}
             * name : 蒂姆·罗宾斯
             * id : 1054521
             */

            private String alt;
            private Avatars avatars;
            private String name;
            private String id;

            public Casts(){

            }

            protected Casts(Parcel in) {
                alt = in.readString();
                avatars = in.readParcelable(Avatars.class.getClassLoader());
                name = in.readString();
                id = in.readString();
            }

            public static final Creator<Casts> CREATOR = new Creator<Casts>() {
                @Override
                public Casts createFromParcel(Parcel in) {
                    return new Casts(in);
                }

                @Override
                public Casts[] newArray(int size) {
                    return new Casts[size];
                }
            };

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public Avatars getAvatars() {
                return avatars;
            }

            public void setAvatars(Avatars avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(alt);
                dest.writeParcelable(avatars, flags);
                dest.writeString(name);
                dest.writeString(id);
            }


            public static class Avatars implements Parcelable{
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public Avatars(){

                }

                protected Avatars(Parcel in) {
                    small = in.readString();
                    large = in.readString();
                    medium = in.readString();
                }

                public static final Creator<Avatars> CREATOR = new Creator<Avatars>() {
                    @Override
                    public Avatars createFromParcel(Parcel in) {
                        return new Avatars(in);
                    }

                    @Override
                    public Avatars[] newArray(int size) {
                        return new Avatars[size];
                    }
                };

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(small);
                    dest.writeString(large);
                    dest.writeString(medium);
                }
            }
        }

        public static class Directors implements Parcelable{
            /**
             * alt : https://movie.douban.com/celebrity/1047973/
             * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg"}
             * name : 弗兰克·德拉邦特
             * id : 1047973
             */

            private String alt;
            private AvatarsX avatars;
            private String name;
            private String id;

            public Directors(){

            }

            protected Directors(Parcel in) {
                alt = in.readString();
                avatars = in.readParcelable(AvatarsX.class.getClassLoader());
                name = in.readString();
                id = in.readString();
            }

            public static final Creator<Directors> CREATOR = new Creator<Directors>() {
                @Override
                public Directors createFromParcel(Parcel in) {
                    return new Directors(in);
                }

                @Override
                public Directors[] newArray(int size) {
                    return new Directors[size];
                }
            };

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsX getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsX avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(alt);
                dest.writeParcelable(avatars, flags);
                dest.writeString(name);
                dest.writeString(id);
            }


            public static class AvatarsX implements Parcelable{
                /**
                 * small : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg
                 * large : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg
                 * medium : https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p230.jpg
                 */

                private String small;
                private String large;
                private String medium;

                public AvatarsX(){

                }

                protected AvatarsX(Parcel in) {
                    small = in.readString();
                    large = in.readString();
                    medium = in.readString();
                }

                public static final Creator<AvatarsX> CREATOR = new Creator<AvatarsX>() {
                    @Override
                    public AvatarsX createFromParcel(Parcel in) {
                        return new AvatarsX(in);
                    }

                    @Override
                    public AvatarsX[] newArray(int size) {
                        return new AvatarsX[size];
                    }
                };

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(small);
                    dest.writeString(large);
                    dest.writeString(medium);
                }
            }
        }
    }
}
