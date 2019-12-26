package com.news.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@TableName("tb_article")
public class Article implements Serializable {

	private static final long serialVersionUID = 7364843935032059246L;
	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 文章标题
	 */
	private String title;

	/**
	 * 文章内容
	 *
	 */
	private String href;

	/**
	 * 来源
	 */
	private String source;

	/**
	 * 文章图片url
	 */
	@TableField("image_url")
	private String imageUrl;

	/**
	 * 发布时间
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 * 创建者
	 */
	@TableField("create_by")
	private String createBy;

	/**
	 * 阅读次数
	 */
	@TableField("read_num")
	private Integer readNum;

	/**
	 * 阅读次数
	 */
	@TableField("comment_num")
	private Integer commentNum;

	/**
	 * 收藏数
	 */
	@TableField("like_num")
	private Integer likeNum;
}
