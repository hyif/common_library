
package com.common.library.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.common.library.R;

/**
 * 在此写用途
 *
 * @author: 黄一凡
 * @date: 2017-03-03
 */
public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;

  private RecyclerView.Adapter mInnerAdapter;
  private View mLoadMoreView;
  private int mLoadMoreLayoutId = R.layout.view_more_loading;
  private MoreViewHolder mMoreViewHolder;

  private boolean hasMore = true;

  public LoadMoreWrapper(RecyclerView.Adapter adapter) {
    mInnerAdapter = adapter;
  }

  private boolean hasLoadMore() {
    return mLoadMoreView != null || mLoadMoreLayoutId != 0;
  }

  private boolean isShowLoadMore(int position) {
    return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
  }

  @Override
  public int getItemViewType(int position) {
    if (isShowLoadMore(position)) {
      return ITEM_TYPE_LOAD_MORE;
    }
    return mInnerAdapter.getItemViewType(position);
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == ITEM_TYPE_LOAD_MORE) {
      if (mLoadMoreView != null) {
        mMoreViewHolder = new MoreViewHolder(mLoadMoreView);
      } else {
        mLoadMoreView = LayoutInflater.from(parent.getContext()).inflate(mLoadMoreLayoutId, parent,
            false);
        mMoreViewHolder = new MoreViewHolder(mLoadMoreView);
      }
      if (!hasMore) {
        //mMoreViewHolder.rootView.setVisibility(View.GONE);
        mLoadMoreView.setVisibility(View.GONE);
      }
      return mMoreViewHolder;
    }
    return mInnerAdapter.onCreateViewHolder(parent, viewType);
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isShowLoadMore(position)) {
      if (mOnLoadMoreListener != null && hasMore) {
        mOnLoadMoreListener.onLoadMoreRequested();
      }
      return;
    }
    mInnerAdapter.onBindViewHolder(holder, position);
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView,
        new WrapperUtils.SpanSizeCallback() {
          @Override
          public int getSpanSize(GridLayoutManager layoutManager,
              GridLayoutManager.SpanSizeLookup oldLookup, int position) {
            if (isShowLoadMore(position)) {
              return layoutManager.getSpanCount();
            }
            if (oldLookup != null) {
              return oldLookup.getSpanSize(position);
            }
            return 1;
          }
        });
  }

  @Override
  public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    mInnerAdapter.onViewAttachedToWindow(holder);

    if (isShowLoadMore(holder.getLayoutPosition())) {
      setFullSpan(holder);
    }
  }

  private void setFullSpan(RecyclerView.ViewHolder holder) {
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

    if (lp != null
        && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
      StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

      p.setFullSpan(true);
    }
  }

  @Override
  public int getItemCount() {
    return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
  }

  public interface OnLoadMoreListener {
    void onLoadMoreRequested();
  }

  private OnLoadMoreListener mOnLoadMoreListener;

  public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
    if (loadMoreListener != null) {
      mOnLoadMoreListener = loadMoreListener;
    }
    return this;
  }

  public void setNoMore() {
    hasMore = false;
    if (mLoadMoreView != null) {
      mMoreViewHolder.pb.setVisibility(View.GONE);
      mMoreViewHolder.prompt.setVisibility(View.VISIBLE);
      mMoreViewHolder.prompt.setText("暂无更多内容");
    }
  }

  private void setHasMore() {
    hasMore = true;
    if (mLoadMoreView != null) {
      mMoreViewHolder.pb.setVisibility(View.VISIBLE);
      mMoreViewHolder.prompt.setVisibility(View.VISIBLE);
      mMoreViewHolder.prompt.setText("正在加载中...");
    }
  }

  public void setPullLoadMore(boolean hasLoadMore) {
    hasMore = hasLoadMore;
    if (mLoadMoreView != null) {
      if (hasLoadMore) {
        setHasMore();
      } else {
        mMoreViewHolder.rootView.setVisibility(View.GONE);
      }
    }
  }

  class MoreViewHolder extends RecyclerView.ViewHolder {
    View rootView;
    ProgressBar pb;
    TextView prompt;

    public MoreViewHolder(View itemView) {
      super(itemView);
      rootView = itemView.findViewById(R.id.root_view);
      pb = (ProgressBar) itemView.findViewById(R.id.loading_progressbar);
      prompt = (TextView) itemView.findViewById(R.id.loading_hint_text);
    }
  }
}
