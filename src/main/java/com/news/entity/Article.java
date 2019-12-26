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
	 * ���±���
	 */
	private String title;

	/**
	 * ��������
	 *
	 */
	private String href;

	/**
	 * ��Դ
	 */
	private String source;

	/**
	 * ����ͼƬurl
	 */
	@TableField("image_url")
	private String imageUrl;

	/**
	 * ����ʱ��
	 */
	@TableField("create_time")
	private Date createTime;

	/**
	 * ������
	 */
	@TableField("create_by")
	private String createBy;

	/**
	 * �Ķ�����
	 */
	@TableField("read_num")
	private Integer readNum;

	/**
	 * �Ķ�����
	 */
	@TableField("comment_num")
	private Integer commentNum;

	/**
	 * �ղ���
	 */
	@TableField("like_num")
	private Integer likeNum;
}
