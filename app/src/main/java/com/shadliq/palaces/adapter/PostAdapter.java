//package com.nureddin.sadliq.adapter;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.text.Html;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupMenu;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.dreasy.R;
//import com.dreasy.api.PostAPI;
//import com.dreasy.api.ReportAPI;
//import com.dreasy.constant.SizeFolder;
//import com.dreasy.dto.response.DressResponseDTO;
//import com.dreasy.dto.response.FileResponseDTO;
//import com.dreasy.dto.response.PostResponseDTO;
//import com.dreasy.dto.response.UserDTO;
//import com.dreasy.service.CustomCallback;
//import com.dreasy.service.UserService;
//import com.dreasy.util.ActivityUtil;
//import com.dreasy.util.DateUtil;
//import com.dreasy.util.ImageHelper;
//import com.dreasy.util.PopupMenuUtil;
//import com.dreasy.util.RetrofitUtil;
//import com.dreasy.util.UserUtil;
//import com.yarolegovich.discretescrollview.DSVOrientation;
//import com.yarolegovich.discretescrollview.DiscreteScrollView;
//import com.yarolegovich.discretescrollview.transform.ScaleTransformer;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.recyclerview.widget.RecyclerView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Retrofit;
//
//public class PostAdapter extends RecyclerView.Adapter<CustomViewHolder> implements DiscreteScrollView.OnItemChangedListener {
//
//    private List<PostResponseDTO> posts = new ArrayList<>();
//    private LayoutInflater layoutInflater;
//    private Activity activity;
//    private ActivityUtil activityUtil;
//    private ImageHelper imageHelper;
//    private static int VIEW_TYPE_ITEM = 1;
//    private static int VIEW_TYPE_LOADING = 2;
//    private UserService userService;
//    private Integer currentUserId;
//    private Retrofit retrofit;
//    private PopupMenuUtil popupMenuUtil;
//    private Date now = new Date();
//
//
//    public PostAdapter(LayoutInflater layoutInflater, Activity activity) {
//        this.layoutInflater = layoutInflater;
//        this.activity = activity;
//        imageHelper = new ImageHelper(activity);
//        userService = new UserService(activity);
//        retrofit = RetrofitUtil.getInstance(activity);
//        currentUserId = UserService.getCurrentUserData().getCurrentUserId();
//        popupMenuUtil = new PopupMenuUtil(activity);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (posts.get(position) != null)
//            return VIEW_TYPE_ITEM;
//        else
//            return VIEW_TYPE_LOADING;
//    }
//
//    @NonNull
//    @Override
//    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View root;
//        activityUtil = new ActivityUtil(activity);
//        if (viewType == VIEW_TYPE_ITEM) {
//            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);
//            return new PostViewHolder(root);
//        } else {
//            root = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_progress, parent, false);
//            return new ProgressViewHolder(root);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
//        if (holder instanceof PostViewHolder) {
//            PostViewHolder postViewHolder = (PostViewHolder) holder;
//            PostResponseDTO post = posts.get(position);
//            final UserDTO user = post.getUser();
//            RelativeLayout postOptionsLayout = postViewHolder.postOptionsLayout;
//            postViewHolder.userName.setText(user.getName());
//            postViewHolder.userSurname.setText(user.getSurname());
//            postViewHolder.description.setText(post.getDescription());
//            postViewHolder.date.setText(DateUtil.getPostDate(post.getDate(), now));
//            String url = UserUtil.getPhotoUrl(user, SizeFolder.SMALL);
//            imageHelper.load(url, postViewHolder.userImage, R.drawable.com_facebook_profile_picture_blank_portrait);
//            List<DressResponseDTO> dresses = post.getDresses();
//            PostImageAdapter postImageAdapter = new PostImageAdapter(layoutInflater, activity, post, dresses);
//            postViewHolder.itemPicker.setAdapter(postImageAdapter);
//            postViewHolder.userName.setOnClickListener(v -> userService.openUserProfile(user.getId()));
//            postViewHolder.userSurname.setOnClickListener(v -> userService.openUserProfile(user.getId()));
//            postViewHolder.frameLayout.setOnClickListener(v -> userService.openUserProfile(user.getId()));
//
//            popupMenuUtil.setPostOptionsMenu(postOptionsLayout, user, posts, post);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return posts.size();
//    }
//
//    @Override
//    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {
//    }
//
//    public void addNullData() {
//        posts.add(null);
//        notifyDataSetChanged();
//    }
//
//    public void clear() {
//        posts.clear();
//    }
//
//    public void removeNull() {
//        posts.remove(posts.size() - 1);
//        notifyDataSetChanged();
//    }
//
//    public void addData(List<PostResponseDTO> posts) {
//        this.posts.addAll(posts);
////        this.notifyItemInserted(getPostSize() - 1);
//        notifyDataSetChanged();
//    }
//
//    class PostViewHolder extends CustomViewHolder {
//        private TextView userName;
//        private TextView userSurname;
//        private TextView date;
//        private TextView description;
//        private RelativeLayout postOptionsLayout;
//        private LinearLayout dotsLayout;
//        private FrameLayout frameLayout;
//        private ImageView userImage;
//        private DiscreteScrollView itemPicker;
//        private TextView voteCount;
//        private TextView commentCount;
//
//
//        PostViewHolder(View itemView) {
//            super(itemView);
//            userName = itemView.findViewById(R.id.user_name);
//            userSurname = itemView.findViewById(R.id.user_surname);
//            frameLayout = itemView.findViewById(R.id.frame);
//            date = itemView.findViewById(R.id.date);
//            description = itemView.findViewById(R.id.description);
//            userImage = frameLayout.findViewById(R.id.user_image);
//            postOptionsLayout = itemView.findViewById(R.id.post_options_layout);
////            postImageRecycleView = itemView.findViewById(R.id.post_img_item_rec_view);
////            dotsLayout = itemView.findViewById(R.id.layoutDots);
//            itemPicker = itemView.findViewById(R.id.item_picker);
//            itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
//            itemPicker.setOverScrollEnabled(true); //Can also be set using android:overScrollMode xml attribute
////            itemPicker.addOnItemChangedListener(activity);
//            voteCount = itemView.findViewById(R.id.vote_count);
//            commentCount = itemView.findViewById(R.id.comment_count);
//
////            itemPicker.setItemTransitionTimeMillis(DiscreteScrollViewOptions.getTransitionTime());
//            itemPicker.setItemTransformer(new ScaleTransformer.Builder()
//                    .setMinScale(0.8f)
//                    .build());
//            itemPicker.setSlideOnFling(true);
//        }
//    }
//
//    class ProgressViewHolder extends CustomViewHolder {
//        ProgressViewHolder(View itemView) {
//            super(itemView);
//        }
//    }
//}
