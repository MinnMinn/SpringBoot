package friends.management.data;

import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * FriendsRetrieveMapper
 */
@Mapper
public interface FriendsManagementMapper {

    /**
     * query
     * @return List<String>
     */
    @Select("SELECT email FROM users WHERE is_deleted = 0")
    List<String> query();

    /**
     * connectMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Insert(
            "INSERT INTO relationship (user_one_id, user_two_id, status)"
                    + " VALUES (#{userOneId}, #{userTwoId} ,1)"
    )
    Boolean connectMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * getIdsByEmailMapper
     * @param emails List<String>
     * @return  List<Integer>
     */
    @Select({"<script>SELECT id FROM users WHERE email IN <foreach item='item' collection='emails' open='(' separator=',' close=')'>#{item}</foreach></script>"})
    List<Integer> getIdsByEmailMapper(@Param("emails")List<String> emails);

    /**
     * isBlockMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return List<Integer>
     */
    @Select("SELECT user_one_id FROM relationship WHERE user_one_id = #{userOneId} AND user_two_id = #{userTwoId} AND status = 3")
    List<Integer> isBlockMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * getIdByEmailMapper
     * @param email String
     * @return Integer
     */
    @Select("SELECT id FROM users WHERE email = #{email} AND is_deleted = 0")
    Integer getIdByEmailMapper(String email);

    /**
     * getFriendIdsByUserOneIdMapper
     * @param userId Integer
     * @return List<Integer>
     */
    @Select("SELECT user_one_id FROM relationship WHERE user_two_id = #{userId} AND status = 1")
    List<Integer> getFriendIdsByUserOneIdMapper(Integer userId);

    /**
     * getFriendIdsByUserTwoIdMapper
     * @param userId Integer
     * @return List<Integer>
     */
    @Select("SELECT user_two_id FROM relationship WHERE user_one_id = #{userId} AND status = 1")
    List<Integer> getFriendIdsByUserTwoIdMapper(Integer userId);

    /**
     * getFriendListByUserIdMapper
     * @param userIds List<Integer>
     * @return List<String>
     */
    @Select({"<script>SELECT email FROM users WHERE id IN <foreach item='item' collection='userIds' open='(' separator=',' close=')'>#{item}</foreach></script>"})
    List<String> getFriendListByUserIdMapper(@Param("userIds") List<Integer> userIds);

    /**
     * isRelationshipMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    @Select("SELECT user_one_id FROM relationship Where user_one_id = #{userOneId} AND user_two_id = #{userTwoId} ")
    Integer isRelationshipMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * blockFriendUpdateMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Update("UPDATE relationship SET status = 3 Where user_one_id = #{userOneId} AND user_two_id = #{userTwoId}")
    Boolean blockFriendUpdateMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * blockFriendCreateMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Insert("INSERT INTO relationship (user_one_id, user_two_id, status) VALUES (#{userOneId}, #{userTwoId} ,3)")
    Boolean blockFriendCreateMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * subscribeUpdateMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Update("UPDATE relationship SET status = 2 Where user_one_id = #{userOneId} AND user_two_id = #{userTwoId}")
    Boolean subscribeUpdateMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * subscribeCreateMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Boolean
     */
    @Insert("INSERT INTO relationship (user_one_id, user_two_id, status) VALUES (#{userOneId}, #{userTwoId} ,2)")
    Boolean subscribeCreateMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * getFriendIdsHasUpdateByUserOneIdMapper
     * @param userId Integer
     * @return List<Integer>
     */
    @Select("SELECT user_one_id FROM relationship WHERE user_two_id = #{userId} AND (status = 1 OR status =2)")
    List<Integer> getFriendIdsHasUpdateByUserOneIdMapper(Integer userId);

    /**
     * getFriendIdsHasUpdateByUserTwoIdMapper
     * @param userId Integer
     * @return List<Integer>
     */
    @Select("SELECT user_two_id FROM relationship WHERE user_one_id = #{userId} AND (status = 1 OR status =2)")
    List<Integer> getFriendIdsHasUpdateByUserTwoIdMapper(Integer userId);

    /**
     * isFriendMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    @Select("SELECT user_one_id FROM relationship WHERE user_one_id = #{userOneId} AND user_two_id = #{userTwoId} AND status =1")
    Integer isFriendMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    /**
     * isSubscribeMapper
     * @param userOneId Integer
     * @param userTwoId Integer
     * @return Integer
     */
    @Select("SELECT user_one_id FROM relationship WHERE user_one_id = #{userOneId} AND user_two_id = #{userTwoId} AND status = 2")
    Integer isSubscribeMapper(@Param("userOneId") Integer userOneId,@Param("userTwoId") Integer userTwoId);

    @Select({"<script>SELECT user_two_id FROM relationship WHERE user_one_id = #{userOneId} AND user_two_id IN <foreach item='item' collection='userIdsMention' open='(' separator=',' close=')'>#{item}</foreach> AND status =3</script>"})
    List<Integer> isBlockUpdateUserOneMapper(@Param("userOneId") Integer userOneId,@Param("userIdsMention") List<Integer> userIdsMention);
    @Select({"<script>SELECT user_one_id FROM relationship WHERE user_two_id = #{userTwoId} AND user_one_id IN <foreach item='item' collection='userIdsMention' open='(' separator=',' close=')'>#{item}</foreach> AND status =3</script>"})
    List<Integer> isBlockUpdateUserTwoMapper(@Param("userTwoId") Integer userTwoId,@Param("userIdsMention") List<Integer> userIdsMention);
}
