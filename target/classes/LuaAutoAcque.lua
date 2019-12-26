--判断该用户是否秒杀过，如果已秒则不允许再秒
local hasSecKill=redis.call('sismember',ARGV[1],KEYS[1])       
if hasSecKill ~=0 then
    return 0;
end

 --设置抢单标识
--redis.call('set',KEYS[1],1);
--设置过期时间
--redis.call('expire',KEYS[1],30000);

--check库存
for goodsNum=2,#KEYS  do
    local goodsStock=redis.call('get',KEYS[goodsNum]);
    if goodsStock< ARGV[goodsNum] then
        return 2;
    end
end

--扣库存
for goodsNum=2,#KEYS do
    redis.call('DECRBY',KEYS[goodsNum],ARGV[goodsNum]);
end

-- 所有抢单成功的用户
redis.call('sadd',ARGV[#ARGV],KEYS[1]);

return 1;
